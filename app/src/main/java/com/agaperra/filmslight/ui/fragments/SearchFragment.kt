package com.agaperra.filmslight.ui.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.transition.TransitionManager
import com.agaperra.filmslight.R
import com.agaperra.filmslight.databinding.FragmentMovieBinding
import com.agaperra.filmslight.databinding.FragmentSearchBinding
import com.agaperra.filmslight.domain.model.AppState
import com.agaperra.filmslight.domain.model.Movie
import com.agaperra.filmslight.ui.adapters.MovieListAdapter
import com.agaperra.filmslight.ui.adapters.listeners.OnMovieClickListener
import com.agaperra.filmslight.ui.viewmodel.MovieViewModel
import com.agaperra.filmslight.utils.Constants
import com.agaperra.filmslight.utils.Constants.searchData
import com.agaperra.filmslight.utils.launchWhenStarted
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import java.util.*

@ExperimentalCoroutinesApi
class SearchFragment : Fragment(R.layout.fragment_search) {

    @ExperimentalCoroutinesApi
    private val movieViewModel: MovieViewModel by activityViewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val movieListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieListAdapter(onMovieClickListener = object : OnMovieClickListener {
            override fun onItemClick(movie: Movie) {
                requireView().findNavController()
                    .navigate(SearchFragmentDirections.openMovie(movieId = movie.id))
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.arrowBack.setOnClickListener {
            requireView().findNavController().navigateUp()
        }
        setSearching()
    }

    private fun doInitialization() {
        binding.mainRecycler.adapter = movieListAdapter
        startObserve(movieViewModel.searchMovies)
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

    private fun setSearching() {

        binding.searchView.setOnSearchClickListener {

            changeSearchViewSize(0.7)
            binding.movieTitle.visibility = View.INVISIBLE
        }
        binding.searchView.setOnCloseListener {

            changeSearchViewSize(0.15)
            binding.movieTitle.visibility = View.VISIBLE
            false
        }

        binding.searchView.apply {
            setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return when (searchData.trim()) {
                        "" -> false
                        else -> {
                            movieViewModel.textChanged(searchData)
                            doInitialization()
                            onActionViewCollapsed()
                            changeSearchViewSize(0.15)
                            binding.movieTitle.visibility = View.VISIBLE
                            binding.searchView.setOnCloseListener { false }
                            true
                        }
                    }
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchData = newText
                    movieViewModel.textChanged(searchData)
                    doInitialization()
                    return true
                }
            })
        }
    }

    private fun changeSearchViewSize(size: Double) {

        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)

        val sceneRoot = binding.container as ViewGroup
        val square: View = sceneRoot.findViewById(R.id.searchView)
        val newSquareSize = metrics.widthPixels * size

        TransitionManager.beginDelayedTransition(sceneRoot)

        val params = square.layoutParams
        params.width = newSquareSize.toInt()
        square.layoutParams = params
    }
}