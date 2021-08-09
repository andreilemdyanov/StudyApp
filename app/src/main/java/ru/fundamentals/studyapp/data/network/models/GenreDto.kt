package ru.fundamentals.studyapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.fundamentals.studyapp.data.models.Genre

@Serializable
data class GenreDto(
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: Int
)

fun GenreDto.toGenre() = Genre(id, name)