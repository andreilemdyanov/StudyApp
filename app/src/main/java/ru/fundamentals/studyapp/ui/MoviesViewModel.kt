package ru.fundamentals.studyapp.ui


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.data.MovieElement
import ru.fundamentals.studyapp.data.loadMovies

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val _mutableMoviesList = MutableLiveData<List<MovieElement>>()
    val moviesList: LiveData<List<MovieElement>> get() = _mutableMoviesList

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _mutableMoviesList.postValue(loadMovies(application))
        }
    }

}