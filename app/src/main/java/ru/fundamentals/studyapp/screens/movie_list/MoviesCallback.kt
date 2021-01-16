package ru.fundamentals.studyapp.screens.movie_list

import androidx.recyclerview.widget.DiffUtil
import ru.fundamentals.studyapp.data.models.MovieElement

class MoviesCallback : DiffUtil.ItemCallback<MovieElement>() {

    override fun areItemsTheSame(oldItem: MovieElement, newItem: MovieElement): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieElement, newItem: MovieElement): Boolean {
        return oldItem == newItem
    }
}