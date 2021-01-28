package ru.fundamentals.studyapp.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ConfigDao {
    @Query("SELECT * FROM config")
    suspend fun getConfig(): Config
}