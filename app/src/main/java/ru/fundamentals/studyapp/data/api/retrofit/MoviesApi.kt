package ru.fundamentals.studyapp.data.api.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.fundamentals.studyapp.data.api.retrofit.models.MovieItem
import ru.fundamentals.studyapp.data.api.retrofit.models.MoviesPopularResponse

interface MoviesApi {
    @GET("movie/popular")
    suspend fun getMoviesResponse(@Query("api_key") apiKey: String): MoviesPopularResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieItem

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCrew(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    )
}