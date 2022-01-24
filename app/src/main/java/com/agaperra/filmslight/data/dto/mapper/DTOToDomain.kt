package com.agaperra.filmslight.data.dto.mapper

import com.agaperra.filmslight.data.dto.credits.CastResponse
import com.agaperra.filmslight.data.dto.movies.MovieDetailsResponce
import com.agaperra.filmslight.data.dto.movies.MovieResponse
import com.agaperra.filmslight.domain.model.*

@JvmName("toDomainMoviesResponse")
fun MovieResponse.toDomain(): List<Movie> = results.map { movie ->
    Movie(
        movie.id,
        movie.original_title,
        movie.overview,
        movie.vote_average,
        movie.poster_path,
        movie.release_date,
        movie.title
    )
}

@JvmName("toDomainCreditsResponse")
fun CastResponse.toDomain(): List<Actor> = cast.map { actor ->
    Actor(
        profile_path = actor.profile_path,
        id = actor.id,
        name = actor.name
    )
}

@JvmName("toDomainMovieDetailsResponse")
fun MovieDetailsResponce.toDomain(): MovieFull = MovieFull(
    id = id,
    original_title = original_title,
    overview = overview,
    poster_path = poster_path,
    release_date = release_date,
    title = title,
    vote_average = vote_average,
    vote_count = vote_count,
    genres = genres.map { it.name },
    production_countries = production_countries,
    budget = budget,
    revenue = revenue,
    runtime = runtime,
    status = status,
    popularity = popularity
)

