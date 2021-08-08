package ru.fundamentals.studyapp.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.fundamentals.studyapp.data.room.ListTypeConverters

@Entity(tableName = "config")
@TypeConverters(ListTypeConverters::class)
data class ConfigDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 1,
//    @ColumnInfo(name = "images")
//    val images: ImagesDto,
//    @ColumnInfo(name = "change_keys")
//    val changeKeys: List<String>
    @ColumnInfo(name = "secure_base_url")
    val secureBaseUrl: String,
    @ColumnInfo(name = "poster_size")
    val posterSize: String,
    @ColumnInfo(name = "backdrop_size")
    val backdropSize: String,
    @ColumnInfo(name = "profile_size")
    val profileSize: String
)