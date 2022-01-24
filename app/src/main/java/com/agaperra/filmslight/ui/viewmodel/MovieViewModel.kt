package com.agaperra.filmslight.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.filmslight.domain.model.*
import com.agaperra.filmslight.domain.use_case.GetMovieDetails
import com.agaperra.filmslight.domain.use_case.GetPopularMovies
import com.agaperra.filmslight.domain.use_case.SearchMovie
import com.agaperra.filmslight.domain.use_case.ShowCredits
import com.agaperra.filmslight.ui.interactor.StringInteractor
import com.agaperra.filmslight.utils.network.ConnectionState
import com.agaperra.filmslight.utils.network.NetworkStatusListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.*
import java.util.concurrent.ScheduledFuture
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class MovieViewModel @Inject constructor(
    private val stringInteractor: StringInteractor,
    private val getPopularMovies: GetPopularMovies,
    private val searchMovie: SearchMovie,
    private val detailsMovie: GetMovieDetails,
    private val showCredits: ShowCredits,
    networkStatusListener: NetworkStatusListener,
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<AppState<List<Movie>>>(AppState.Loading())
    var popularMovies = _popularMovies.asStateFlow()

    private val _detailsMovie = MutableStateFlow<AppState<MovieFull>>(AppState.Loading())
    var infoMovie = _detailsMovie.asStateFlow()

    private val _searchMovies = MutableStateFlow<AppState<List<Movie>>>(AppState.Loading())
    var searchMovies = _searchMovies.asStateFlow()

    private val _moviesLoading = MutableStateFlow(true)
    val moviesLoading = _moviesLoading.asStateFlow()

    private val _movieCast =
        MutableStateFlow<AppState<List<Actor>>>(value = AppState.Loading())
    val movieCast = _movieCast.asStateFlow()

    private var future: ScheduledFuture<*>? = null

    init {
        networkStatusListener.networkStatus.onEach { status ->
            when (status) {
                ConnectionState.Available -> {
                    getMovies(Locale.getDefault().language, 1)
                }
                ConnectionState.Unavailable -> {
                    if (_popularMovies.value.data != null) _popularMovies.value =
                        AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMovies(
        lang: String,
        page: Int
    ) {
        getPopularMovies(lang = lang, page = page).onEach { result ->
            when (result) {
                is AppState.Success -> {
                    _popularMovies.value = result
                    _moviesLoading.value = false
                }
                is AppState.Loading -> {
                    _moviesLoading.value = true
                    if (future?.isCancelled == false) future?.cancel(false)
                }
                is AppState.Error -> {
                    _moviesLoading.value = false
                    _popularMovies.value = result
                    Timber.e(result.message?.name)
                }
            }

        }.launchIn(viewModelScope)
    }

//    fun getMovieInfo(id: Int) {
//        detailsMovie(id, Locale.getDefault().language).onEach { movie ->
//            _detailsMovie.value = movie
//        }.launchIn(viewModelScope)
//
//        showCredits(id, Locale.getDefault().language).onEach { cast ->
//            _movieCast.value = cast
//        }.launchIn(viewModelScope)
//    }


    private fun searchMovies(
        query: String, lang: String,
        page: Int
    ){
        searchMovie(lang = lang, query = query, page = page).onEach { result ->
            when (result) {
                is AppState.Success -> {
                    _searchMovies.value = result
                    _moviesLoading.value = false
                }
                is AppState.Loading -> {
                    _moviesLoading.value = true
                    if (future?.isCancelled == false) future?.cancel(false)
                }
                is AppState.Error -> {
                    _moviesLoading.value = false
                    _searchMovies.value = result
                    Timber.e(result.message?.name)
                }
            }

        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        future?.cancel(false)
    }

    fun textChanged(query: String) {
        when (query) {
            "" -> {
                return
            }
            else -> {
                searchMovies(lang = Locale.getDefault().language, query = query, page = 1)
            }
        }


    }

    fun getDetailsMovies(
       id: Int, lang: String
    ){
        detailsMovie(lang = lang, id = id).onEach { result ->
            when (result) {
                is AppState.Success -> {
                    println(result.message?.name)
                    _detailsMovie.value = result
                    _moviesLoading.value = false
                }
                is AppState.Loading -> {
                    println(result.message?.name)
                    _moviesLoading.value = true
                    if (future?.isCancelled == false) future?.cancel(false)
                }
                is AppState.Error -> {
                    _moviesLoading.value = false
                    _detailsMovie.value = result
                    Timber.e(result.message?.name)
                }
            }

        }.launchIn(viewModelScope)

        showCredits(id, Locale.getDefault().language).onEach { cast ->
            _movieCast.value = cast
        }.launchIn(viewModelScope)
    }


    fun getRuntime(runtime: Int?): String {
        runtime?.let {
            val hours = runtime / 60
            val minutes = runtime % 60
            return (String.format("%d", hours) + stringInteractor.textHour + String.format(
                "%02d",
                minutes
            ) + stringInteractor.textMinute)
        } ?: return stringInteractor.textUnknownRuntime

    }

    fun getCountry(productionCountries: List<ProductionCountries>?): String {
        productionCountries?.let {
            return if (productionCountries.isEmpty()) {
                stringInteractor.textUnknown
            } else {
                productionCountries.first().name
            }
        } ?: return stringInteractor.textUnknown
    }

    fun getDate(date: String) = date.take(4)

    fun getOverview(overview: String?): String {
        overview?.let {
            return overview
        } ?: return stringInteractor.textNoOverview

    }


}