package ru.fundamentals.studyapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class MovieElement {
    abstract val id: Long

    @Parcelize
    data class Movie(
        override val id: Long,
        val title: String,
        val overview: String,
        val poster: String,
        val backdrop: String,
        var ratings: Float,
        val numberOfRatings: Int,
        val minimumAge: Int,
        val runtime: Int,
        var isLike: Boolean = false,
        val genres: List<Genre> = emptyList(),
        val actors: List<Actor> = emptyList()
    ) : MovieElement(), Parcelable

    @Parcelize
    data class Header(
        override val id: Long,
        val title: String,
        val image: String,
    ) : MovieElement(), Parcelable
}