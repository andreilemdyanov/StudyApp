package ru.fundamentals.studyapp.data.api.retrofit.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastItem(
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    var profilePath: String = "default",
    @SerialName("id")
    val id: Int
)