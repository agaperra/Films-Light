package com.agaperra.filmslight.domain.repository

import com.agaperra.filmslight.data.dto.credits.CastResponse
import com.agaperra.filmslight.domain.model.Actor
import com.agaperra.filmslight.domain.model.Movie
import com.agaperra.filmslight.domain.model.MovieFull

interface FilmsRepository {

    suspend fun getPopularMovies(
        lang: String,
        page: Int
    ) : List<Movie>

    suspend fun searchMovie(
        lang: String,
        query: String,
        page: Int
    ) : List<Movie>

    suspend fun getMovieDetails(
        id: Int,
        lang: String,
    ) : MovieFull

    suspend fun showCredits(
        id: Int,
        lang: String,
    ) : List<Actor>


}