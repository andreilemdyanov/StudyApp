package ru.fundamentals.studyapp.screens.movie_details

import androidx.recyclerview.widget.DiffUtil
import ru.fundamentals.studyapp.data.models.Actor

class ActorsDiffCallback : DiffUtil.ItemCallback<Actor>() {

    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }
}