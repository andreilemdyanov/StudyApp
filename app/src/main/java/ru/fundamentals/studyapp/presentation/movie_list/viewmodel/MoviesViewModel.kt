package ru.fundamentals.studyapp.presentation.movie_list.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.data.network.api.RetrofitModule
import ru.fundamentals.studyapp.data.network.models.ConfigResponse
import ru.fundamentals.studyapp.data.models.Genre
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.util.API_KEY

class MoviesViewModel : ViewModel() {
    private val _mutableMoviesList = MutableLiveData<Map<Int, MovieElement>>()
    val moviesList: LiveData<Map<Int, MovieElement>> get() = _mutableMoviesList

    private val _mutableConfig = MutableLiveData<ConfigResponse>()
    val config: LiveData<ConfigResponse> get() = _mutableConfig

    init {
        viewModelScope.launch {
            _mutableConfig.postValue(RetrofitModule.configApi.getConfig(API_KEY))
            val genres =
                RetrofitModule.genresApi.getGenresResponse(API_KEY).genres.map { genreItem ->
                    Genre(
                        genreItem.id,
                        genreItem.name
                    )
                }.associateBy { it.id }
            val movies =
                RetrofitModule.moviesApi.getMoviesResponse(API_KEY).results.map { movieItem ->
                    MovieElement.Movie(
                        movieItem.id,
                        movieItem.title,
                        movieItem.overview,
                        _mutableConfig.value!!.images.secureBaseUrl + _mutableConfig.value!!.images.posterSizes[2] + movieItem.posterPath,
                        _mutableConfig.value!!.images.secureBaseUrl + _mutableConfig.value!!.images.backdropSizes[2] + movieItem.backdropPath,
                        movieItem.voteAverage / 2.0,
                        movieItem.voteCount,
                        if (movieItem.adult) 16 else 13,
                        100,
                        false,
                        movieItem.genreIds.map {
                            genres[it] ?: throw IllegalArgumentException("Genre not found")
                        }
                    )
                }
            val moviesResult = mutableListOf<MovieElement>().apply {
                add(0, MovieElement.Header(-1, "Header", "Some image"))
                addAll(movies)
            }
            _mutableMoviesList.postValue(moviesResult.associateBy { it.id })
        }
    }

    fun getMovie(movieId: Int) = _mutableMoviesList.value?.get(movieId)
}