package ru.fundamentals.studyapp.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.fundamentals.studyapp.data.models.Genre
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.data.room.ListTypeConverters

@Entity(tableName = "movies")
@TypeConverters(ListTypeConverters::class)
data class MovieDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Int> = emptyList(),
//    @ColumnInfo(name = "poster_path")
//    val posterPath: String,
//    @ColumnInfo(name = "backdrop_path")
//    val backdropPath: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "adult")
    val adult: Boolean,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int
)

fun MovieDb.toMovie(mapGenres: Map<Int, GenreDb>) =
    MovieElement.Movie(
        id.toInt(),
        title,
        overview,
        "",
        "",
        voteAverage / 2.0,
        voteCount,
        if (adult) 16 else 13,
        100,
        false,
        genreIds.map {
            Genre(
                (mapGenres[it] ?: error("")).id,
                (mapGenres[it] ?: error("")).name
            )
        }
    )