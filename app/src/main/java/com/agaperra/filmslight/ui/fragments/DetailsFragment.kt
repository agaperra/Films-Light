package com.agaperra.filmslight.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.agaperra.filmslight.R
import com.agaperra.filmslight.databinding.FragmentInfoBinding
import com.agaperra.filmslight.domain.model.AppState
import com.agaperra.filmslight.domain.model.MovieFull
import com.agaperra.filmslight.ui.adapters.ActorsAdapter
import com.agaperra.filmslight.ui.adapters.GenresAdapter
import com.agaperra.filmslight.ui.viewmodel.MovieViewModel
import com.agaperra.filmslight.utils.Constants.IMAGE_URL
import com.agaperra.filmslight.utils.launchWhenStarted
import com.squareup.picasso.Picasso
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import timber.log.Timber.Forest.e
import java.util.*

@ExperimentalCoroutinesApi
class DetailsFragment: Fragment(R.layout.fragment_info) {

    private val movieViewModel: MovieViewModel by activityViewModels()

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()
    private val genresAdapter by lazy(LazyThreadSafetyMode.NONE) {
        GenresAdapter()
    }

    private val movieCastAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ActorsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
        startObserve()
    }

    private fun initialize() = with(binding) {
        arrowBack.setOnClickListener {
            requireView().findNavController().navigateUp()
        }
        recyclerViewGenres.adapter = genresAdapter
        recyclerActors.adapter = movieCastAdapter
    }

    private fun startObserve(){
        movieViewModel.getDetailsMovies(args.movieId, Locale.getDefault().language)
        movieViewModel.infoMovie.onEach { result ->
            when (result) {
                is AppState.Success -> {
                    showResult(result.data)
                }
                is AppState.Error -> {
                    println(result.message.toString())
                    e(result.message.toString())
                }
                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)

        movieViewModel.movieCast.onEach { result ->
            when (result) {
                is AppState.Success -> {
                    movieCastAdapter.submitList(result.data)
                }
                is AppState.Error -> {
                    Timber.e(result.message.toString())
                }
                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)
    }

    private fun showResult(movie: MovieFull?) = with(binding) {
        movie?.let {
            setPoster(it.poster_path)
            setName(it.title)
            setDate(movieViewModel.getDate(it.release_date))
            setRating(it.vote_average)
            setRatingCount(it.vote_count)
            setBudget(it.budget)
            setRevenue(it.revenue)
            setCountry(movieViewModel.getCountry(it.production_countries))
            setOverview(movieViewModel.getOverview(it.overview))
            setRuntime(movieViewModel.getRuntime(it.runtime))
            genresAdapter.setItems(it.genres)
        }
        progressBar.hide()
        topContentGroup.isVisible = true
    }

    private fun setName(name: String) {
        binding.movieTitle.text = name
    }

    private fun setPoster(poster_path: String?) {

        Picasso.get().load("${IMAGE_URL}${poster_path}")
            .placeholder(R.drawable.ic_baseline_no_photography_24)
            .into(binding.imageMovie)
    }

    private fun setOverview(overview: String) {
        binding.textOverview.text = overview
    }

    @SuppressLint("SetTextI18n")
    fun setRating(rating_average: Double) {
        binding.textRating.text = "$rating_average"
    }

    @SuppressLint("SetTextI18n")
    fun setRatingCount(rating_count: Int) {
        binding.textPeople.text = "$rating_count"
    }

    @SuppressLint("SetTextI18n")
    private fun setDate(date: String) {
        binding.date.text = date
    }

    @SuppressLint("SetTextI18n")
    fun setBudget(budget: Int) {
        binding.budget.text = "$$budget"
    }

    @SuppressLint("SetTextI18n")
    fun setRevenue(revenue: Int) {
        binding.revenue.text = "$$revenue"
    }

    @SuppressLint("SetTextI18n")
    private fun setRuntime(runtime: String) {
        binding.runtime.text = runtime
    }

    @SuppressLint("SetTextI18n")
    private fun setCountry(name: String) {
        binding.country.text = name
    }


}