package ru.fundamentals.studyapp.data.mappers

import ru.fundamentals.studyapp.data.models.Genre
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.data.room.GenreDb
import ru.fundamentals.studyapp.data.room.MovieDb

object MoviesMapperDbToUi {

    fun transformList(
        movies: List<MovieDb>,
        genresDb: List<GenreDb>
    ): List<MovieElement.Movie> {
        val mapGenres = genresDb.associateBy { it.id }
        return movies.map { movie ->
            MovieElement.Movie(
                movie.id.toInt(),
                movie.title,
                movie.overview,
                "",
                "",
                movie.voteAverage / 2.0,
                movie.voteCount,
                if (movie.adult) 16 else 13,
                100,
                false,
                movie.genreIds.map {
                    Genre(
                        (mapGenres[it] ?: error("")).id.toInt(),
                        (mapGenres[it] ?: error("")).name
                    )
                }
            )
        }
    }
}