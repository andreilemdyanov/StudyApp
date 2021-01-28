package ru.fundamentals.studyapp.presentation.movie_list.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.App
import ru.fundamentals.studyapp.data.mappers.*
import ru.fundamentals.studyapp.data.network.api.RetrofitModule
import ru.fundamentals.studyapp.data.network.models.ConfigResponse
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.data.room.AppDatabase
import ru.fundamentals.studyapp.util.API_KEY

class MoviesViewModel : ViewModel() {
    private val _mutableMoviesList = MutableLiveData<List<MovieElement>>()
    val moviesList: LiveData<List<MovieElement>> get() = _mutableMoviesList

    private val _mutableConfig = MutableLiveData<ConfigResponse>()
    val config: LiveData<ConfigResponse> get() = _mutableConfig
    val db: AppDatabase = App.instance?.appDatabase!!

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            Log.d("M_MoviesViewModel", "$exception")
        }) {

//            val configPersist = db.configDao().getConfig()
            val genresPersist = db.genreDao().getAll()
            val moviesPersist = db.movieDao().getAll()

            val dbMovieRes = mutableListOf<MovieElement>().apply {
                add(0, MovieElement.Header(-1, "Header", "Some image"))
                addAll(MoviesMapperDbToUi.transformList(moviesPersist, genresPersist))
            }

            _mutableMoviesList.postValue(dbMovieRes)

            _mutableConfig.postValue(RetrofitModule.configApi.getConfig(API_KEY))
            val genres =
                GenresMapperApiToUi.transformList(RetrofitModule.genresApi.getGenresResponse(API_KEY).genres)
                    .also {
                        db.genreDao().insertAll(GenresMapperUiToDb.transformList(it))
                    }
            val movies =
                MoviesMapperApiToUi.transformList(
                    RetrofitModule.moviesApi.getMoviesResponse(API_KEY).results,
                    _mutableConfig.value!!,
                    genres
                ).also {
                    db.movieDao().insertAll(MoviesMapperUiToDb.transformList(it))
                }

            val moviesResult = mutableListOf<MovieElement>().apply {
                add(0, MovieElement.Header(-1, "Header", "Some image"))
                addAll(movies)
            }
            _mutableMoviesList.postValue(moviesResult)
        }
    }

    fun getMovie(movieId: Int) = _mutableMoviesList.value?.find { it.id == movieId }
}