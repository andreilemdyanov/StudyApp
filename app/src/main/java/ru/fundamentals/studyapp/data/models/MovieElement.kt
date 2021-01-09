package ru.fundamentals.studyapp.data.models

import ru.fundamentals.studyapp.data.api.retrofit.models.GenreItem


sealed class MovieElement {
    abstract val id: Int

    data class Movie(
        override val id: Int,
        val title: String,
        val overview: String,
        val poster: String,
        val backdrop: String,
        var ratings: Double,
        val numberOfRatings: Int,
        val minimumAge: Int,
        val runtime: Int,
        var isLike: Boolean = false,
        val genres: List<Genre> = emptyList(),
    ) : MovieElement()

    data class Header(
        override val id: Int,
        val title: String,
        val image: String,
    ) : MovieElement()
}