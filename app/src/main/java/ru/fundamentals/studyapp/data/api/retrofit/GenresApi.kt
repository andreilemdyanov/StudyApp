package ru.fundamentals.studyapp.data.api.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import ru.fundamentals.studyapp.data.api.retrofit.models.GenresResponse

interface GenresApi {
    @GET("genre/movie/list")
    suspend fun getGenresResponse(@Query("api_key") apiKey: String): GenresResponse
}