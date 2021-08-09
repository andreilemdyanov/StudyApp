package ru.fundamentals.studyapp.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.fundamentals.studyapp.data.network.models.GenresResponse

interface GenresApi {
    @GET("genre/movie/list")
    suspend fun getGenresResponse(): GenresResponse
}