package com.agaperra.filmslight.data.dto.movies

import com.agaperra.filmslight.domain.model.Movie

data class MovieResponse(
    val page: Int,
    val total_pages: Int,
    val results: ArrayList<Movie>
)