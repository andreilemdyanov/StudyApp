package ru.fundamentals.studyapp.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.fundamentals.studyapp.data.network.models.ConfigResponse

interface ConfigApi {
    @GET("configuration")
    suspend fun getConfig(): ConfigResponse
}