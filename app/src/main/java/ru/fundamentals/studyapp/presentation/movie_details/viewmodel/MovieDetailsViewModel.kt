package ru.fundamentals.studyapp.presentation.movie_details.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.App
import ru.fundamentals.studyapp.data.network.api.RetrofitModule
import ru.fundamentals.studyapp.data.network.models.CastDto
import ru.fundamentals.studyapp.data.network.models.RunTimeDto

class MovieDetailsViewModel : ViewModel() {

    private val _mutableRunTime = MutableLiveData<RunTimeDto>()
    val runTimeDto: LiveData<RunTimeDto> get() = _mutableRunTime

    private val _mutableCrewList = MutableLiveData<List<CastDto>>()
    val crewList: LiveData<List<CastDto>> get() = _mutableCrewList

    private val retrofit : RetrofitModule = App.instance.retrofit

    fun getMovieRuntime(movieId: Int) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            Log.d("M_MovieDetailsViewModel", "$exception")

        }) {
            _mutableRunTime.postValue(retrofit.moviesApi.getMovieDetails(movieId))
        }
    }

    fun getMovieCrew(movieId: Int) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            Log.d("M_MovieDetailsViewModel", "$exception")
        }) {
            _mutableCrewList.postValue(retrofit.moviesApi.getMovieCrew(movieId).cast)
        }
    }
}