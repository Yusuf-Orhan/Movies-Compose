package com.yusuforhan.android.movies.domain.repository

import com.yusuforhan.android.movies.common.Resource
import com.yusuforhan.android.movies.data.model.MovieDetail
import com.yusuforhan.android.movies.data.model.Movies

interface MoviesRepository {
    suspend fun getMovies() : Resource<Movies>
    suspend fun getMovieDetail(imdbId : String) : Resource<MovieDetail>
}