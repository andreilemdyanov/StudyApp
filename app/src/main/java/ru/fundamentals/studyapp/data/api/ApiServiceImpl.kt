package ru.fundamentals.studyapp.data.api

import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.data.loadMovies
import ru.fundamentals.studyapp.util.APP_ACTIVITY

class ApiServiceImpl : ApiService {
    override suspend fun getMovies(): List<MovieElement> = loadMovies(APP_ACTIVITY)
}