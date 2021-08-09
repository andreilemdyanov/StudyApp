package ru.fundamentals.studyapp.data.models


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
        var runtime: Int,
        var isLike: Boolean = false,
        val genres: List<Genre> = emptyList(),
    ) : MovieElement()

    data class Header(
        override val id: Int,
        val title: String,
        val image: String,
    ) : MovieElement()
}