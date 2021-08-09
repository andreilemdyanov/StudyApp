package ru.fundamentals.studyapp.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.fundamentals.studyapp.data.models.Config
import ru.fundamentals.studyapp.data.models.Genre
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.data.room.models.GenreDb

@Serializable
data class MovieDto(
    @SerialName("overview")
    val overview: String,
    @SerialName("title")
    val title: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("id")
    val id: Int,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("vote_count")
    val voteCount: Int,
    @Transient
    val runTime: Int = 0
)

fun MovieDto.toMovie(config: Config, mapGenres: Map<Int, Genre>) =
    MovieElement.Movie(
        id,
        title,
        overview,
        "${config.secureBaseUrl}${config.posterSize}${posterPath}",
        "${config.secureBaseUrl}${config.backdropSize}${backdropPath ?: posterPath}",
        voteAverage / 2.0,
        voteCount,
        if (adult) 16 else 13,
        100,
        false,
        genreIds.map {
            mapGenres[it] ?: throw IllegalArgumentException("Genre not found")
        }
    )
