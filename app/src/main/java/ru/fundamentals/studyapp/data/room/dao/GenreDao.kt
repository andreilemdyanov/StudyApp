package ru.fundamentals.studyapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.fundamentals.studyapp.data.room.models.GenreDb

@Dao
interface GenreDao {

    @Query("SELECT * FROM genres")
    suspend fun getAll(): List<GenreDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<GenreDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genre: GenreDb): Long
}