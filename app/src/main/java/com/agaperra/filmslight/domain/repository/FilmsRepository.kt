package com.agaperra.filmslight.domain.repository

import com.agaperra.filmslight.domain.model.CastResponse
import com.agaperra.filmslight.domain.model.MovieFull
import com.agaperra.filmslight.domain.model.MovieResponse

interface FilmsRepository {

    suspend fun getPopularMovies(
        key: String,
        lang: String,
        page: Int
    ) : MovieResponse

    suspend fun searchMovie(
        key: String,
        lang: String,
        query: String,
        page: Int
    ) : MovieResponse

    suspend fun getMovieDetails(
        id: Int,
        key: String,
        lang: String,
    ) : MovieFull

    suspend fun showCredits(
        id: Int,
        key: String,
        lang: String,
    ) : CastResponse


}