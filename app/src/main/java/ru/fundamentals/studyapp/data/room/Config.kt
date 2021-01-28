package ru.fundamentals.studyapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.fundamentals.studyapp.data.network.models.ImagesDto

@Entity(tableName = "config")
@TypeConverters(ListTypeConverters::class)
data class Config(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 1,
    @ColumnInfo(name = "images")
    val images: ImagesDto,
    @ColumnInfo(name = "change_keys")
    val changeKeys: List<String>
)