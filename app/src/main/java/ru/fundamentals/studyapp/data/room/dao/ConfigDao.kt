package ru.fundamentals.studyapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.fundamentals.studyapp.data.room.models.ConfigDb

@Dao
interface ConfigDao {

    @Query("SELECT * FROM config WHERE id = 1")
    suspend fun getConfig(): ConfigDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(configDb: ConfigDb): Long
}