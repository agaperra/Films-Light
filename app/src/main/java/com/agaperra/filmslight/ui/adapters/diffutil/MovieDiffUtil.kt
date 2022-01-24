package com.agaperra.filmslight.ui.adapters.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.agaperra.filmslight.domain.model.Movie

class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id== newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
}