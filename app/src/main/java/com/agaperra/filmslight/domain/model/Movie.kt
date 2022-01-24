package com.agaperra.filmslight.domain.model

data class Movie(
    val id: Int,
    val original_title: String,
    val overview: String?,
    val vote_average: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String
)