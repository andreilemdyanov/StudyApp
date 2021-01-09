package ru.fundamentals.studyapp.data.api.retrofit.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastItem(
    @SerialName("cast_id")
    val castId: Int,
    @SerialName("character")
    val character: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profilePath: String,
    @SerialName("id")
    val id: Int
)