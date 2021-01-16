package ru.fundamentals.studyapp.screens


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.data.Repository

class MoviesViewModel(repository: Repository) : ViewModel() {
    private val _mutableMoviesList = MutableLiveData<Map<Long, MovieElement>>()
    val moviesList: LiveData<Map<Long, MovieElement>> get() = _mutableMoviesList

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _mutableMoviesList.postValue(repository.getMovies()?.associateBy { it.id })
        }
    }

    fun getMovie(movieId: Long) = _mutableMoviesList.value?.get(movieId)
}