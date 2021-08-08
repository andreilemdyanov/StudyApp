package ru.fundamentals.studyapp.data

import ru.fundamentals.studyapp.data.models.MovieElement

sealed class MoviesResult
class ValidResult(val result: List<MovieElement>): MoviesResult()
class ErrorResult(val e: Throwable): MoviesResult()