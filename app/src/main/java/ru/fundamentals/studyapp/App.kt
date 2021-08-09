package ru.fundamentals.studyapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.room.Room
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.fundamentals.studyapp.data.network.CacheInterceptor
import ru.fundamentals.studyapp.data.network.TokenInterceptor
import ru.fundamentals.studyapp.data.network.api.RetrofitModule
import ru.fundamentals.studyapp.data.room.AppDatabase
import ru.fundamentals.studyapp.data.room.AppDatabase.Companion.DATABASE_NAME
import java.io.File

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private val client by lazy {
        OkHttpClient().newBuilder()
            .cache(
                Cache(
                    directory = File(cacheDir, "http_cache"),
                    maxSize = 50L * 1024L * 1024L
                )
            )
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(TokenInterceptor())
            .addInterceptor(CacheInterceptor())
            .build()
    }

    val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    val retrofit by lazy { RetrofitModule(client) }

    fun hasNetwork(): Boolean {
        var isConnected = false
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    companion object {
        lateinit var instance: App
    }
}