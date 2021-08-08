package ru.fundamentals.studyapp.presentation.movie_list.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.App
import ru.fundamentals.studyapp.data.ErrorResult
import ru.fundamentals.studyapp.data.MoviesRepository
import ru.fundamentals.studyapp.data.ValidResult
import ru.fundamentals.studyapp.data.models.Config
import ru.fundamentals.studyapp.data.network.api.RetrofitModule
import ru.fundamentals.studyapp.data.room.AppDatabase

class MoviesViewModel : ViewModel() {
    private val _mutableMoviesList = MutableLiveData<ValidResult>()
    val moviesList: LiveData<ValidResult> get() = _mutableMoviesList

    private val _mutableConfig = MutableLiveData<Config>()
    val config: LiveData<Config> get() = _mutableConfig

    private val _mutableError = MutableLiveData<ErrorResult>()
    val error: LiveData<ErrorResult> get() = _mutableError

    private val db: AppDatabase = App.instance.appDatabase

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            Log.d("M_MoviesViewModel", "$exception")
        }) {
            val repo = MoviesRepository(
                db.configDao(),
                db.genreDao(),
                db.movieDao(),
                RetrofitModule.configApi,
                RetrofitModule.genresApi,
                RetrofitModule.moviesApi
            )

            repo.getConfigDb().collect {
                _mutableConfig.postValue(it)
            }

            try {
                repo.getConfig().collect {
                    _mutableConfig.postValue(it)
                }
            } catch (e: Exception) {
                _mutableError.postValue(ErrorResult(e))
                Log.d("M_MoviesViewModel", "config not load")
            }


            repo.getMoviesDb().collect {
                _mutableMoviesList.postValue(ValidResult(it))
            }

            try {
                repo.getMovies(_mutableConfig.value!!).collect {
                    _mutableMoviesList.postValue(ValidResult(it))
                }
            } catch (e: Exception) {
                _mutableError.postValue(ErrorResult(e))
                Log.d("M_MoviesViewModel", "movies not load")
            }
        }
    }

    fun getMovie(movieId: Int) =
        _mutableMoviesList.value!!.result.find { it.id == movieId }

}