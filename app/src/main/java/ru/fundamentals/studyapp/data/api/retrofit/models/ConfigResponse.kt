package ru.fundamentals.studyapp.data.api.retrofit.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigResponse(
    @SerialName("images")
    val images: Images,
    @SerialName("change_keys")
    val changeKeys: List<String>
)