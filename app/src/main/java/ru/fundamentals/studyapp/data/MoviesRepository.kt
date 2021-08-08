package ru.fundamentals.studyapp.data

import android.util.Log
import kotlinx.coroutines.flow.flow
import ru.fundamentals.studyapp.data.mappers.*
import ru.fundamentals.studyapp.data.models.Config
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.data.network.api.ConfigApi
import ru.fundamentals.studyapp.data.network.api.GenresApi
import ru.fundamentals.studyapp.data.network.api.MoviesApi
import ru.fundamentals.studyapp.data.room.dao.ConfigDao
import ru.fundamentals.studyapp.data.room.models.ConfigDb
import ru.fundamentals.studyapp.data.room.dao.GenreDao
import ru.fundamentals.studyapp.data.room.dao.MovieDao
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
        emit(ConfigMapperDbToUi.transform(configPersist ?: ConfigDb(1, "", "", "", "")))
    }

    fun getConfig() = flow {
        val configPersist = configDao.getConfig()
        emit(ConfigMapperDbToUi.transform(configPersist ?: ConfigDb(1, "", "", "", "")))

        val config = ConfigMapperApiToUi.transform(configApi.getConfig(API_KEY)).also {
            configDao.insert(ConfigMapperUiToDb.transform(it))
        }
        emit(config)
    }

    fun getMoviesDb() = flow<List<MovieElement>> {
        val genresPersist = genreDao.getAll()
        val moviesPersist = movieDao.getAll()
        Log.d("M_MoviesRepository", "genresPersist = $genresPersist")
        Log.d("M_MoviesRepository", "moviesPersist = $moviesPersist")

        val dbMovieRes = mutableListOf<MovieElement>().apply {
            add(0, MovieElement.Header(-1, "Header", "Some image"))
            addAll(MoviesMapperDbToUi.transformList(moviesPersist, genresPersist))
        }
        emit(dbMovieRes)
    }

    fun getMovies(config: Config) = flow<List<MovieElement>> {
        val genresPersist = genreDao.getAll()
        val moviesPersist = movieDao.getAll()
        Log.d("M_MoviesRepository", "genresPersist = $genresPersist")
        Log.d("M_MoviesRepository", "moviesPersist = $moviesPersist")

        val dbMovieRes = mutableListOf<MovieElement>().apply {
            add(0, MovieElement.Header(-1, "Header", "Some image"))
            addAll(MoviesMapperDbToUi.transformList(moviesPersist, genresPersist))
        }
        emit(dbMovieRes)

//        val config = RetrofitModule.configApi.getConfig(API_KEY)
        val genres =
            GenresMapperApiToUi.transformList(genreApi.getGenresResponse(API_KEY).genres)
                .also {
                    genreDao.insertAll(GenresMapperUiToDb.transformList(it))
                }
        val movies =
            MoviesMapperApiToUi.transformList(
                movieApi.getMoviesResponse(API_KEY).results,
                config,
                genres
            ).also {
                movieDao.insertAll(MoviesMapperUiToDb.transformList(it))
            }

        val moviesResult = mutableListOf<MovieElement>().apply {
            add(0, MovieElement.Header(-1, "Header", "Some image"))
            addAll(movies)
        }
        emit(moviesResult)
    }
}
