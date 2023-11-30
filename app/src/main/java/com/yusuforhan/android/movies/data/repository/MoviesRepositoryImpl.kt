package com.yusuforhan.android.movies.data.repository

import com.yusuforhan.android.movies.common.Resource
import com.yusuforhan.android.movies.data.model.MovieDetail
import com.yusuforhan.android.movies.data.model.Movies
import com.yusuforhan.android.movies.data.source.remote.MoviesApi
import com.yusuforhan.android.movies.domain.repository.MoviesRepository
import java.net.URLDecoder
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val service: MoviesApi
) : MoviesRepository {
    override suspend fun getMovies(searchString: String): Resource<Movies> =
        try {
            Resource.Success(service.getMovies(searchString = searchString))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "")
        }

    override suspend fun getMovieDetail(imdbId: String): Resource<MovieDetail> =
        try {
            Resource.Success(service.getMovieDetail(imdbId = imdbId))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "")
        }

}
