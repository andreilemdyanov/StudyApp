package ru.fundamentals.studyapp

import android.app.Application
import androidx.room.Room
import ru.fundamentals.studyapp.data.room.AppDatabase
import ru.fundamentals.studyapp.data.room.AppDatabase.Companion.DATABASE_NAME

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        var instance: App? = null
    }

}