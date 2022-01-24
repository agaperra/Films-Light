package com.agaperra.filmslight.ui.adapters.listeners

import com.agaperra.filmslight.domain.model.Movie

interface OnMovieClickListener {
    fun onItemClick(movie: Movie)
}