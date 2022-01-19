package com.agaperra.filmslight.domain.model

data class MovieResponse(
    val page: Int,
    val total_pages: Int,
    val results: ArrayList<Movie>
)