package ru.fundamentals.studyapp.data.models

import ru.fundamentals.studyapp.data.room.models.GenreDb
import java.io.Serializable

data class Genre(val id: Int, val name: String) : Serializable

fun Genre.toGenreDb() = GenreDb(id, name)