package ru.fundamentals.studyapp.data.api.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import ru.fundamentals.studyapp.data.api.retrofit.models.ConfigResponse

interface ConfigApi {
    @GET("configuration")
    suspend fun getConfig(@Query("api_key") apiKey: String): ConfigResponse
}