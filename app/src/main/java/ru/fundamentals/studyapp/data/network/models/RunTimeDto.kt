package ru.fundamentals.studyapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunTimeDto (
    @SerialName("runtime")
    val runTime: Int
)