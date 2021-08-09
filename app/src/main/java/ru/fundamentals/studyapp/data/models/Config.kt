package ru.fundamentals.studyapp.data.models

import ru.fundamentals.studyapp.data.room.models.ConfigDb

data class Config(
    val secureBaseUrl: String,
    val posterSize: String,
    val backdropSize: String,
    val profileSize: String
)

fun Config.toConfigDb() =
    ConfigDb(
        1,
        secureBaseUrl,
        posterSize,
        backdropSize,
        profileSize
    )