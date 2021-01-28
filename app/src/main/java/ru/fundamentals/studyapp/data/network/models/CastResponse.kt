package ru.fundamentals.studyapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse(
    @SerialName("cast")
	val cast: List<CastDto>,
    @SerialName("id")
	val id: Int
)
