package ru.fundamentals.studyapp.presentation.movie_list.viewmodel


import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.App
import ru.fundamentals.studyapp.data.ErrorResult
import ru.fundamentals.studyapp.data.MoviesRepository
import ru.fundamentals.studyapp.data.ValidResult
import ru.fundamentals.studyapp.data.models.Config
import ru.fundamentals.studyapp.data.models.Genre
import ru.fundamentals.studyapp.data.network.api.RetrofitModule
import ru.fundamentals.studyapp.data.room.AppDatabase
import ru.fundamentals.studyapp.data.room.models.toConfig

class MoviesViewModel : ViewModel() {
    private val _mutableMoviesList = MutableLiveData<ValidResult>()
    val moviesList: LiveData<ValidResult> get() = _mutableMoviesList

    private val _mutableConfig = MutableLiveData<Config>()
    val config: LiveData<Config> get() = _mutableConfig

    private val _mutableGenres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> get() = _mutableGenres

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
            repo.getGenresDb().collect {
                _mutableGenres.postValue(it)
            }

            try {
                repo.getConfig().collect {
                    _mutableConfig.postValue(it)
                }
            } catch (e: Exception) {
                _mutableError.postValue(ErrorResult(e))
                Log.d("M_MoviesViewModel", "config not load")
            }

            try {
                repo.getGenres().collect {
                    _mutableGenres.postValue(it)
                }
            } catch (e: Exception) {
                _mutableError.postValue(ErrorResult(e))
                Log.d("M_MoviesViewModel", "genres not load")
            }


            repo.getMoviesDb().collect {
                _mutableMoviesList.postValue(ValidResult(it))
            }

            try {
                repo.getMovies(_mutableConfig.value!!, _mutableGenres.value!!).collect {
                    Log.d("MoviesViewModel", it.toString())
                    _mutableMoviesList.postValue(ValidResult(it))
                }
            } catch (e: Exception) {
                _mutableError.postValue(ErrorResult(e))
                e.printStackTrace()
                Log.d("M_MoviesViewModel", "movies not load")
            }
        }
    }

    fun getMovie(movieId: Int) =
        _mutableMoviesList.value!!.result.find { it.id == movieId }

}