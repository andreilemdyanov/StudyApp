package ru.fundamentals.studyapp.data.mappers

import ru.fundamentals.studyapp.data.models.Genre
import ru.fundamentals.studyapp.data.room.GenreDb

object GenresMapperUiToDb {
    fun transformList(genres: List<Genre>): List<GenreDb> {
        return genres.map { genre ->
            GenreDb(
                genre.id.toLong(),
                genre.name
            )
        }
    }
}