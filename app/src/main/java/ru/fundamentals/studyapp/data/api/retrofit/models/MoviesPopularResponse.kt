package ru.fundamentals.studyapp.data.api.retrofit.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesPopularResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("results")
    val results: List<MovieItem>,
    @SerialName("total_results")
    val totalResults: Int
)

