package com.yusuforhan.android.movies.di

import com.yusuforhan.android.movies.data.repository.MoviesRepositoryImpl
import com.yusuforhan.android.movies.data.source.remote.MoviesApi
import com.yusuforhan.android.movies.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMoviesRepository(service : MoviesApi) : MoviesRepository = MoviesRepositoryImpl(service)
}