package com.agaperra.filmslight.data.api

import com.agaperra.filmslight.domain.model.CastResponse
import com.agaperra.filmslight.domain.model.MovieFull
import com.agaperra.filmslight.domain.model.MovieResponse
import com.agaperra.filmslight.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsApi {

    @GET(value = Constants.POPULAR_MOVIES)
    fun getPopularMovies(
        @Query("api_key") key: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET(value = Constants.SEARCH_MOVIES)
    fun searchMovie(
        @Query("api_key") key: String,
        @Query("language") lang: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET(value = "/3/movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") key: String,
        @Query("language") lang: String
    ): MovieFull

    @GET(value = "/3/movie/{id}/credits")
    fun showCredits(
        @Path("id") id: Int,
        @Query("api_key") key: String,
        @Query("language") lang: String
    ): CastResponse
}