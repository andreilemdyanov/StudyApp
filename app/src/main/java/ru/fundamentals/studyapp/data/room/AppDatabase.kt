package ru.fundamentals.studyapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieDb::class, GenreDb::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
//    abstract fun configDao(): ConfigDao

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