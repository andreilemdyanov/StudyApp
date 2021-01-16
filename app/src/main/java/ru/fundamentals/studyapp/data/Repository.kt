package ru.fundamentals.studyapp.data

import ru.fundamentals.studyapp.data.api.ApiHelper

class Repository(private val apiHelper: ApiHelper) {
    suspend fun getMovies() = apiHelper.getMovies()
}