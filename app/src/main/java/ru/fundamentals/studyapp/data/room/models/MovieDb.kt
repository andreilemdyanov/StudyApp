package ru.fundamentals.studyapp.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
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
    val genreIds: List<Long>,
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