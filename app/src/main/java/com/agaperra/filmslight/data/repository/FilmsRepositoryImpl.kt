package com.agaperra.filmslight.data.repository

import com.agaperra.filmslight.data.api.FilmsApi
import com.agaperra.filmslight.domain.model.CastResponse
import com.agaperra.filmslight.domain.model.MovieFull
import com.agaperra.filmslight.domain.model.MovieResponse
import com.agaperra.filmslight.domain.repository.FilmsRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FilmsRepositoryImpl @Inject constructor(
    private val filmsApi: FilmsApi
) : FilmsRepository {
    override suspend fun getPopularMovies(key: String, lang: String, page: Int): MovieResponse =
        filmsApi.getPopularMovies(key = key, lang = lang, page = page)

    override suspend fun searchMovie(
        key: String,
        lang: String,
        query: String,
        page: Int
    ): MovieResponse = filmsApi.searchMovie(key = key, lang = lang, query = query, page = page)

    override suspend fun getMovieDetails(id: Int, key: String, lang: String): MovieFull =
        filmsApi.getMovieDetails(id = id, key = key, lang = lang)

    override suspend fun showCredits(id: Int, key: String, lang: String): CastResponse =
        filmsApi.showCredits(id = id, key = key, lang = lang)


}