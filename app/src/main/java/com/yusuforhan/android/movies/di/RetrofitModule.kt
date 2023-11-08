package com.yusuforhan.android.movies.di

import com.yusuforhan.android.movies.common.Constants
import com.yusuforhan.android.movies.data.source.remote.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideMovieApi() : MoviesApi = Retrofit
        .Builder()
        .baseUrl(Constants.MOVIE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MoviesApi::class.java)
}