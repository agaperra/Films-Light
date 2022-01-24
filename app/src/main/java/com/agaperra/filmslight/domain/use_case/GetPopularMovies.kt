package com.agaperra.filmslight.domain.use_case

import android.util.Log
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.agaperra.filmslight.domain.model.AppState
import com.agaperra.filmslight.domain.model.ErrorState
import com.agaperra.filmslight.domain.repository.FilmsRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val filmsRepository: FilmsRepository
) {
    operator fun invoke(
        lang: String,
        page: Int
    ) = flow {
        emit(AppState.Loading())
        try {
            val response =
                filmsRepository.getPopularMovies(lang, page)
            emit(AppState.Success(data = response))
        } catch (exception: HttpException) {
            if (exception.code() != 400)
                emit(AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION))
            Timber.e(exception)
        } catch (exception: Exception) {
            emit(AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION))
            Timber.e(exception)
        }
    }


}