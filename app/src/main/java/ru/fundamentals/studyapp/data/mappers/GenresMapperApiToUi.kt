package ru.fundamentals.studyapp.data.mappers

import ru.fundamentals.studyapp.data.models.Genre
import ru.fundamentals.studyapp.data.network.models.GenreDto

object GenresMapperApiToUi {
    fun transformList(genres: List<GenreDto>): List<Genre> {
        return genres.map { genreItem ->
            Genre(
                genreItem.id,
                genreItem.name
            )
        }
    }
}