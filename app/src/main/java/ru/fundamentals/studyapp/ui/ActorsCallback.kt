package ru.fundamentals.studyapp.ui

import androidx.recyclerview.widget.DiffUtil
import ru.fundamentals.studyapp.data.Actor

class ActorsCallback : DiffUtil.ItemCallback<Actor>() {

    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }
}