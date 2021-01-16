package ru.fundamentals.studyapp.data.api

import ru.fundamentals.studyapp.data.models.MovieElement

interface ApiService {
    suspend fun getMovies(): List<MovieElement>
}