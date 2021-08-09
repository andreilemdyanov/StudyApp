package ru.fundamentals.studyapp.data.network.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.fundamentals.studyapp.data.network.TokenInterceptor

object RetrofitModule {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    private val client = OkHttpClient().newBuilder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(TokenInterceptor())
        .build()

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val moviesApi: MoviesApi = retrofit.create()
    val genresApi: GenresApi = retrofit.create()
    val configApi: ConfigApi = retrofit.create()
}