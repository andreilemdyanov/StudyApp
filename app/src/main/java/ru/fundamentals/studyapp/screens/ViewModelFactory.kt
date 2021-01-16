package ru.fundamentals.studyapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fundamentals.studyapp.data.Repository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
