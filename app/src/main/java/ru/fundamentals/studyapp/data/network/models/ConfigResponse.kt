package ru.fundamentals.studyapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.fundamentals.studyapp.data.models.Config

@Serializable
data class ConfigResponse(
    @SerialName("images")
    val imagesDto: ImagesDto,
    @SerialName("change_keys")
    val changeKeys: List<String>
)

fun ConfigResponse.toConfig() =
    Config(
        imagesDto.secureBaseUrl,
        imagesDto.posterSizes[2],
        imagesDto.backdropSizes[2],
        imagesDto.profileSizes[1]
    )