package ru.fundamentals.studyapp.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.data.api.retrofit.RetrofitModule
import ru.fundamentals.studyapp.data.api.retrofit.models.CastItem
import ru.fundamentals.studyapp.data.api.retrofit.models.RunTime
import ru.fundamentals.studyapp.util.API_KEY

class MovieDetailsViewModel : ViewModel() {

    private val _mutableRunTime = MutableLiveData<RunTime>()
    val runTime: LiveData<RunTime> get() = _mutableRunTime

    private val _mutableCrewList = MutableLiveData<List<CastItem>>()
    val crewList: LiveData<List<CastItem>> get() = _mutableCrewList

    fun getMovieRuntime(movieId: Int) {
        viewModelScope.launch {
            _mutableRunTime.postValue(RetrofitModule.moviesApi.getMovieDetails(movieId, API_KEY))
        }
    }

    fun getMovieCrew(movieId: Int) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            Log.d(
                "M_MovieDetailsViewModel",
                "$exception"
            )
        }) {
            _mutableCrewList.postValue(RetrofitModule.moviesApi.getMovieCrew(movieId, API_KEY).cast)
        }
    }
}