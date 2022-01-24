package com.agaperra.filmslight.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agaperra.filmslight.R
import com.agaperra.filmslight.databinding.ItemGenreBinding

class GenresAdapter(private val genresList: ArrayList<String> = arrayListOf()) :
    RecyclerView.Adapter<GenresAdapter.MovieGenresListViewHolder>() {

    inner class MovieGenresListViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: String) {
            binding.genreItem.text = genre
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieGenresListViewHolder(
        ItemGenreBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_genre,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: MovieGenresListViewHolder, position: Int) =
        holder.bind(genresList[position])

    override fun getItemCount() = genresList.count()

    fun setItems(genres: List<String>) {
        if (genresList.isNotEmpty()) {
            val size = genresList.size
            genresList.clear()
            notifyItemRangeRemoved(0, size)
        }
        genresList.addAll(genres)
        notifyItemRangeChanged(0, genres.size)
    }


}