package ru.fundamentals.studyapp.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "genres",
//    foreignKeys = [ForeignKey(
//        entity = Movie::class,
//        parentColumns = arrayOf("genre_ids"),
//        childColumns = arrayOf("id"),
//        onDelete = NO_ACTION
//    )]
)
data class GenreDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String
)