package com.agaperra.filmslight.data.repository

import com.agaperra.filmslight.data.api.FilmsApi
import com.agaperra.filmslight.data.dto.credits.CastResponse
import com.agaperra.filmslight.data.dto.mapper.toDomain
import com.agaperra.filmslight.domain.model.MovieFull
import com.agaperra.filmslight.data.dto.movies.MovieResponse
import com.agaperra.filmslight.domain.repository.FilmsRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FilmsRepositoryImpl @Inject constructor(
    private val filmsApi: FilmsApi
) : FilmsRepository {
    override suspend fun getPopularMovies(lang: String, page: Int)=
        filmsApi.getPopularMovies( lang = lang, page = page).toDomain()

    override suspend fun searchMovie(
        lang: String,
        query: String,
        page: Int
    )= filmsApi.searchMovie(lang = lang, query = query, page = page).toDomain()

    override suspend fun getMovieDetails(id: Int, lang: String) =
        filmsApi.getMovieDetails(id = id, lang = lang).toDomain()

    override suspend fun showCredits(id: Int, lang: String)=
        filmsApi.showCredits(id = id, lang = lang).toDomain()


}