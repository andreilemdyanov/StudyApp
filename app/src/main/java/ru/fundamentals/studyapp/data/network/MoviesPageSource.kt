package ru.fundamentals.studyapp.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.fundamentals.studyapp.data.models.MovieElement
import ru.fundamentals.studyapp.data.network.api.ConfigApi
import ru.fundamentals.studyapp.data.network.api.GenresApi
import ru.fundamentals.studyapp.data.network.api.MoviesApi
import ru.fundamentals.studyapp.data.network.models.toConfig
import ru.fundamentals.studyapp.data.network.models.toGenre
import ru.fundamentals.studyapp.data.network.models.toMovie
import ru.fundamentals.studyapp.util.API_KEY

class MoviesPageSource(
    private val moviesApi: MoviesApi,
    private val configApi: ConfigApi,
    private val genresApi: GenresApi
) : PagingSource<Int, MovieElement>() {
    override fun getRefreshKey(state: PagingState<Int, MovieElement>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieElement> {
//        if (query.isEmpty()) {
//            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
//        }

        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(20)

        val moviesResponse = moviesApi.getMoviesResponse(page)
        val configResponse = configApi.getConfig()
        val genresResponse = genresApi.getGenresResponse()
        val mapGenres = genresResponse.genres
            .map { it.toGenre() }
            .associateBy { it.id }

        return if (moviesResponse.isSuccessful) {
            val movies = checkNotNull(moviesResponse.body()).results.map {
                it.toMovie(
                    configResponse.toConfig(),
                    mapGenres
                )
            }
            val nextKey = if (movies.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(movies, prevKey, nextKey)
        } else {
            LoadResult.Error(HttpException(moviesResponse))
        }
    }
}