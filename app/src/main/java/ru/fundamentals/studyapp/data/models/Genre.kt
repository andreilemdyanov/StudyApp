package ru.fundamentals.studyapp.data.models

import ru.fundamentals.studyapp.data.room.models.GenreDb

data class Genre(val id: Int, val name: String)

fun Genre.toGenreDb() = GenreDb(id, name)