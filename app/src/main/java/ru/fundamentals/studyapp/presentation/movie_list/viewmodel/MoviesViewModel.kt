package ru.fundamentals.studyapp.presentation.movie_list.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.App
import ru.fundamentals.studyapp.data.MoviesRepository
import ru.fundamentals.studyapp.data.models.Config
import ru.fundamentals.studyapp.data.models.Genre
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.data.network.api.RetrofitModule
import ru.fundamentals.studyapp.data.room.AppDatabase

class MoviesViewModel : ViewModel() {
    private val _mutableConfig = MutableLiveData<Config>()
    val config: LiveData<Config> get() = _mutableConfig

    private val _mutableGenres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> get() = _mutableGenres

    private val _mutableError = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _mutableError

    private val db: AppDatabase = App.instance.appDatabase
    private val retrofit: RetrofitModule = App.instance.retrofit

    private val repo by lazy {
        MoviesRepository(
            db.configDao(),
            db.genreDao(),
            db.movieDao(),
            retrofit.configApi,
            retrofit.genresApi,
            retrofit.moviesApi
        )
    }


    val moviesPs: StateFlow<PagingData<MovieElement>> = repo.getMoviesStream()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            Log.d("M_MoviesViewModel", "$exception")
            _mutableError.postValue(exception)
        }) {

            repo.getConfigDb().collect {
                _mutableConfig.postValue(it)
            }
            repo.getGenresDb().collect {
                _mutableGenres.postValue(it)
            }

//            try {
            repo.getConfig().collect {
                _mutableConfig.postValue(it)
            }
//            } catch (e: Exception) {
//                _mutableError.postValue(ErrorResult(e))
//                Log.d("M_MoviesViewModel", "config not load")
//            }

//            try {
            repo.getGenres().collect {
                _mutableGenres.postValue(it)
            }
//            } catch (e: Exception) {
//                _mutableError.postValue(ErrorResult(e))
//                Log.d("M_MoviesViewModel", "genres not load")
//            }
        }
    }
}