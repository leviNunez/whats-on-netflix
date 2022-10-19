package com.android.course.whatsonnetflix.di

import com.android.course.whatsonnetflix.data.remote.repository.ContentsRepositoryImpl
import com.android.course.whatsonnetflix.repository.ContentsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMoviesRepository(
        contentsRepositoryImpl: ContentsRepositoryImpl): ContentsRepository
}