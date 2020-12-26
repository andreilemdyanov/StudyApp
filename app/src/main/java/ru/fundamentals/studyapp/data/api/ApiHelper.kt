package ru.fundamentals.studyapp.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getMovies() = apiService.getMovies()
}