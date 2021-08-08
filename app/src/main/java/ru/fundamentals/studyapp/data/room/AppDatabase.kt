package ru.fundamentals.studyapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.fundamentals.studyapp.data.room.dao.ConfigDao
import ru.fundamentals.studyapp.data.room.dao.GenreDao
import ru.fundamentals.studyapp.data.room.dao.MovieDao
import ru.fundamentals.studyapp.data.room.models.ConfigDb
import ru.fundamentals.studyapp.data.room.models.GenreDb
import ru.fundamentals.studyapp.data.room.models.MovieDb

@Database(entities = [MovieDb::class, GenreDb::class, ConfigDb::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun configDao(): ConfigDao

    companion object {
        const val DATABASE_NAME = "Movies.db"
//        fun newInstance(context: Context) {
//            Room.databaseBuilder(
//                context,
//                AppDatabase::class.java,
//                DATABASE_NAME
//            ).build()

//        val instance: AppDatabase by lazy {
//            Room.databaseBuilder(
//                App.appContext,
//                AppDatabase::class.java,
//                DATABASE_NAME
//            ).build()
        }

}