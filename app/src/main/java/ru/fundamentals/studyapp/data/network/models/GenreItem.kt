package ru.fundamentals.studyapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreItem(
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: Int
)