package com.agaperra.filmslight.data.api

import com.agaperra.filmslight.BuildConfig
import com.agaperra.filmslight.data.dto.credits.CastResponse
import com.agaperra.filmslight.data.dto.movies.MovieDetailsResponce
import com.agaperra.filmslight.domain.model.MovieFull
import com.agaperra.filmslight.data.dto.movies.MovieResponse
import com.agaperra.filmslight.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsApi {

    @GET(value = Constants.POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("api_key") key: String = BuildConfig.FILM_API_KEY,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET(value = Constants.SEARCH_MOVIES)
    suspend fun searchMovie(
        @Query("api_key") key: String = BuildConfig.FILM_API_KEY,
        @Query("language") lang: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET(value = "/3/movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") key: String = BuildConfig.FILM_API_KEY,
        @Query("language") lang: String
    ): MovieDetailsResponce

    @GET(value = "/3/movie/{id}/credits")
    suspend fun showCredits(
        @Path("id") id: Int,
        @Query("api_key") key: String = BuildConfig.FILM_API_KEY,
        @Query("language") lang: String
    ): CastResponse
}