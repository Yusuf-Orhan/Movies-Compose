package com.yusuforhan.android.movies.data.source.remote

import com.yusuforhan.android.movies.common.Constants.API_KEY
import com.yusuforhan.android.movies.common.Constants.DEFAULT_SEARCH
import com.yusuforhan.android.movies.data.model.MovieDetail
import com.yusuforhan.android.movies.data.model.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET(".")
    suspend fun getMovies(
        @Query("apikey") apiKey : String = API_KEY,
        @Query("s") searchString: String = DEFAULT_SEARCH
    ) : Movies
    @GET(".")
    suspend fun getMovieDetail(
        @Query("apikey") apiKey : String = API_KEY,
        @Query("i") imdbId: Int
    ) : MovieDetail
}