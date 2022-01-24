package com.agaperra.filmslight.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agaperra.filmslight.R
import com.agaperra.filmslight.databinding.MovieItemBinding
import com.agaperra.filmslight.domain.model.Movie
import com.agaperra.filmslight.ui.adapters.diffutil.MovieDiffUtil
import com.agaperra.filmslight.ui.adapters.listeners.OnMovieClickListener
import com.agaperra.filmslight.utils.Constants
import com.squareup.picasso.Picasso

class MovieListAdapter(private val onMovieClickListener: OnMovieClickListener) :
    ListAdapter<Movie, MovieListAdapter.MovieListViewHolder>(MovieDiffUtil()) {

    inner class MovieListViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {

            val movie = getItem(itemPosition)

            Picasso.get().load("${Constants.IMAGE_URL}${movie.poster_path}")
                .placeholder(R.drawable.ic_baseline_no_photography_24)
                .into(binding.imageMovie)

            binding.textViewStars.text = movie.vote_average.toString()
            binding.textName.text = movie.title
            binding.textReleaseDate.text =
                movie.release_date.take(4)

            binding.root.
                setOnClickListener {
                    onMovieClickListener.onItemClick(movie = movie)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
       MovieListViewHolder(
            MovieItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.movie_item,
                    parent,
                    false
                )
            )
        )

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) =
        holder.bind(itemPosition = position)

}