package ru.fundamentals.studyapp.data.mappers

import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.data.room.MovieDb

object MoviesMapperUiToDb {

    fun transformList(movies: List<MovieElement.Movie>): List<MovieDb> {
        return movies.map { movieElement ->
            MovieDb(
                movieElement.id.toLong(),
                movieElement.overview,
                movieElement.title,
                movieElement.genres.map { genre -> genre.id.toLong() },
                movieElement.ratings,
                movieElement.minimumAge != 13,
                movieElement.numberOfRatings
            )
        }
    }
}