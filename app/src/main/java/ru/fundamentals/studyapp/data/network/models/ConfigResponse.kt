package ru.fundamentals.studyapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigResponse(
    @SerialName("images")
    val imagesDto: ImagesDto,
    @SerialName("change_keys")
    val changeKeys: List<String>
)