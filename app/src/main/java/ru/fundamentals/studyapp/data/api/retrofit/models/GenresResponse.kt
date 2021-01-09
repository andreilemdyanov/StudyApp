package ru.fundamentals.studyapp.data.api.retrofit.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
	@SerialName("genres")
	val genres: List<GenreItem>
)

