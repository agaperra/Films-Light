package com.agaperra.filmslight.ui.adapters.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.agaperra.filmslight.domain.model.Actor

class CreditsDiffUtil : DiffUtil.ItemCallback<Actor>() {

    override fun areItemsTheSame(oldItem: Actor, newItem: Actor) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor) = oldItem == newItem

}