package ru.fundamentals.studyapp.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.fundamentals.studyapp.data.models.*
import ru.fundamentals.studyapp.data.network.MoviesPageSource
import ru.fundamentals.studyapp.data.network.api.ConfigApi
import ru.fundamentals.studyapp.data.network.api.GenresApi
import ru.fundamentals.studyapp.data.network.api.MoviesApi
import ru.fundamentals.studyapp.data.network.models.toConfig
import ru.fundamentals.studyapp.data.network.models.toGenre
import ru.fundamentals.studyapp.data.network.models.toMovie
import ru.fundamentals.studyapp.data.room.dao.ConfigDao
import ru.fundamentals.studyapp.data.room.dao.GenreDao
import ru.fundamentals.studyapp.data.room.dao.MovieDao
import ru.fundamentals.studyapp.data.room.models.ConfigDb
import ru.fundamentals.studyapp.data.room.models.toConfig
import ru.fundamentals.studyapp.data.room.models.toGenre
import ru.fundamentals.studyapp.data.room.models.toMovie
import ru.fundamentals.studyapp.util.API_KEY

class MoviesRepository(
    private val configDao: ConfigDao,
    private val genreDao: GenreDao,
    private val movieDao: MovieDao,
    private val configApi: ConfigApi,
    private val genreApi: GenresApi,
    private val movieApi: MoviesApi
) {

    fun getConfigDb() = flow {
        val configPersist = configDao.getConfig()
        emit((configPersist ?: ConfigDb(1, "", "", "", "")).toConfig())
    }

    fun getConfig() = flow {
        val configPersist = configDao.getConfig()
        emit((configPersist ?: ConfigDb(1, "", "", "", "")).toConfig())

        val config = configApi.getConfig(API_KEY).toConfig().also {
            configDao.insert(it.toConfigDb())
        }
        emit(config)
    }

    fun getGenresDb() = flow {
        val genresPersist = genreDao.getAll()
        emit(genresPersist.map { it.toGenre() })
    }

    fun getGenres() = flow {
        val genresPersist = genreDao.getAll()
        emit(genresPersist.map { it.toGenre() })
        val genres =
            genreApi.getGenresResponse(API_KEY).genres.map { it.toGenre() }
                .also {
                    genreDao.insertAll(it.map { genre -> genre.toGenreDb() })
                }
        emit(genres)
    }

    fun getMoviesDb() = flow<List<MovieElement>> {
        val genresPersist = genreDao.getAll()
        val moviesPersist = movieDao.getAll()
        Log.d("M_MoviesRepository", "genresPersist = $genresPersist")
        Log.d("M_MoviesRepository", "moviesPersist = $moviesPersist")
        val mapGenres = genresPersist.associateBy { it.id }
        val dbMovieRes = mutableListOf<MovieElement>().apply {
            add(0, MovieElement.Header(-1, "Header", "Some image"))
            addAll(moviesPersist.map { it.toMovie(mapGenres) })
        }
        emit(dbMovieRes)
    }

    fun getMovies(config: Config, genres: List<Genre>) = flow<List<MovieElement>> {
        val genresPersist = genreDao.getAll()
        val moviesPersist = movieDao.getAll()
        Log.d("M_MoviesRepository", "genresPersist = $genresPersist")
        Log.d("M_MoviesRepository", "moviesPersist = $moviesPersist")
        val mapGenresDb = genresPersist.associateBy { it.id }
        val dbMovieRes = mutableListOf<MovieElement>().apply {
            add(0, MovieElement.Header(-1, "Header", "Some image"))
            addAll(moviesPersist.map { it.toMovie(mapGenresDb) })
        }
        emit(dbMovieRes)

        val mapGenres = genres.associateBy { it.id }
        val movies =
            checkNotNull(movieApi.getMoviesResponse(API_KEY).body()).results.map {
                it.toMovie(config, mapGenres)
            }.also {
                movieDao.insertAll(it.map { movie -> movie.toMovieDb() })
            }

        val moviesResult = mutableListOf<MovieElement>().apply {
            add(0, MovieElement.Header(-1, "Header", "Some image"))
            addAll(movies)
        }
        emit(moviesResult)
    }

    fun getMoviesStream(): Flow<PagingData<MovieElement>> {
        return Pager(PagingConfig(20),
            pagingSourceFactory = { MoviesPageSource(movieApi, configApi, genreApi) }
        ).flow
    }
}
