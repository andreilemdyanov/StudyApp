package ru.fundamentals.studyapp.data.mappers

import ru.fundamentals.studyapp.data.models.Config
import ru.fundamentals.studyapp.data.models.Genre
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.data.network.models.ConfigResponse
import ru.fundamentals.studyapp.data.network.models.MovieDto

object MoviesMapperApiToUi {

    fun transformList(
        movies: List<MovieDto>,
        config: Config,
        genres: List<Genre>
    ): List<MovieElement.Movie> {
        val mapGenres = genres.associateBy { it.id }
        return movies.map { movieItem ->
            MovieElement.Movie(
                movieItem.id,
                movieItem.title,
                movieItem.overview,
                config.secureBaseUrl + config.posterSize + movieItem.posterPath,
                config.secureBaseUrl + config.backdropSize + movieItem.backdropPath,
                movieItem.voteAverage / 2.0,
                movieItem.voteCount,
                if (movieItem.adult) 16 else 13,
                100,
                false,
                movieItem.genreIds.map {
                    mapGenres[it] ?: throw IllegalArgumentException("Genre not found")
                }
            )
        }
    }
}