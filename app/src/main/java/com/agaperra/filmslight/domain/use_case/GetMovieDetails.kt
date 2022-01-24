package com.agaperra.filmslight.domain.use_case

import com.agaperra.filmslight.domain.model.AppState
import com.agaperra.filmslight.domain.model.ErrorState
import com.agaperra.filmslight.domain.repository.FilmsRepository
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetMovieDetails  @Inject constructor(
    private val filmsRepository: FilmsRepository
) {
    operator fun invoke(id: Int, lang: String) = flow {
        try {
            val movieDetails = filmsRepository.getMovieDetails(
                id = id, lang = lang
            )
            emit(AppState.Success(data = movieDetails))
        } catch (e: Exception) {
            println("uuuuuuuuuuuuuuuuu")
            println(e.message)
            Timber.e(e)
            emit(AppState.Error(error = ErrorState.NO_FILMS_LOADED))
        }
    }
}