package com.agaperra.filmslight.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agaperra.filmslight.R
import com.agaperra.filmslight.databinding.ActorItemBinding
import com.agaperra.filmslight.domain.model.Actor
import com.agaperra.filmslight.ui.adapters.diffutil.CreditsDiffUtil
import com.agaperra.filmslight.utils.Constants
import com.squareup.picasso.Picasso

class ActorsAdapter(
) : ListAdapter<Actor, ActorsAdapter.ActorsViewHolder>(CreditsDiffUtil()) {

    inner class ActorsViewHolder(private val binding: ActorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val actor = getItem(itemPosition)

            Picasso.get().load("${Constants.IMAGE_URL}${actor.profile_path}")
                .placeholder(R.drawable.ic_baseline_no_photography_24)
                .into(binding.actorPhoto)

            binding.actorName.text = actor.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ActorsViewHolder(
        ActorItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.actor_item,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) =
        holder.bind(itemPosition = position)
}

