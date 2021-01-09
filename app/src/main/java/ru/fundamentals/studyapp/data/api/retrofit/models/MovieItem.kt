package ru.fundamentals.studyapp.data.api.retrofit.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieItem(
    @SerialName("overview")
    val overview: String,
    @SerialName("title")
    val title: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("id")
    val id: Int,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("vote_count")
    val voteCount: Int
)