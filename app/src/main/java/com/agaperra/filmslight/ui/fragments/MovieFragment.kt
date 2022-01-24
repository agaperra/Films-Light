package com.agaperra.filmslight.ui.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.TransitionManager
import com.agaperra.filmslight.R
import com.agaperra.filmslight.databinding.FragmentMovieBinding
import com.agaperra.filmslight.domain.model.AppState
import com.agaperra.filmslight.domain.model.Movie
import com.agaperra.filmslight.ui.adapters.MovieListAdapter
import com.agaperra.filmslight.ui.viewmodel.MovieViewModel
import com.agaperra.filmslight.utils.launchWhenStarted
import com.agaperra.filmslight.ui.adapters.listeners.OnMovieClickListener
import com.agaperra.filmslight.utils.Constants.searchData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MovieFragment : Fragment(R.layout.fragment_movie) {

    private val movieViewModel: MovieViewModel by activityViewModels()

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val movieListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieListAdapter(onMovieClickListener = object : OnMovieClickListener {
            override fun onItemClick(movie: Movie) {
                requireView().findNavController()
                    .navigate(MovieFragmentDirections.openMovie(movieId = movie.id))
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.refresh.setOnRefreshListener {
            refresh(binding.refresh)
        }
        doInitialization()

    }

    private fun refresh(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.postOnAnimationDelayed({
            doInitialization()
            swipeRefreshLayout.isRefreshing = false
        }, 2000)
    }


    private fun doInitialization() {
        binding.search.setOnClickListener {
            requireView().findNavController()
                .navigate(MovieFragmentDirections.openSearch())
        }
        binding.mainRecycler.adapter = movieListAdapter
        movieViewModel.getMovies(Locale.getDefault().language, 1)
        startObserve(movieViewModel.popularMovies)
    }


    private fun setResult(result: AppState<List<Movie>>) {
        when (result) {
            is AppState.Error -> {
                binding.progressBar.isVisible = false
            }
            is AppState.Loading -> {
                binding.progressBar.isVisible = true
            }
            is AppState.Success -> {
                movieListAdapter.submitList(result.data)
                binding.progressBar.isVisible = false
            }
        }
    }

    private fun startObserve(contentSource: StateFlow<AppState<List<Movie>>>) {

        contentSource.onEach { result ->
            setResult(result)
        }.launchWhenStarted(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}