package ru.fundamentals.studyapp.screens


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.fundamentals.studyapp.data.api.retrofit.RetrofitModule
import ru.fundamentals.studyapp.data.models.Genre
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.util.API_KEY

class MoviesViewModel : ViewModel() {
    private val _mutableMoviesList = MutableLiveData<Map<Int, MovieElement>>()
    val moviesList: LiveData<Map<Int, MovieElement>> get() = _mutableMoviesList

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val config = RetrofitModule.configApi.getConfig(API_KEY)
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
                        config.images.secureBaseUrl + config.images.posterSizes[2] + movieItem.posterPath,
                        config.images.secureBaseUrl + config.images.backdropSizes[2] + movieItem.backdropPath,
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