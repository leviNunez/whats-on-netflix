package com.android.course.whatsonnetflix.di

import com.android.course.whatsonnetflix.data.repository.NetflixContentRepositoryImpl
import com.android.course.whatsonnetflix.data.repository.SearchHistoryRepositoryImpl
import com.android.course.whatsonnetflix.domain.repository.NetflixContentRepository
import com.android.course.whatsonnetflix.domain.repository.SearchHistoryRepository
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
    abstract fun bindNetflixContentRepository(
        repositoryImpl: NetflixContentRepositoryImpl
    ): NetflixContentRepository

    @Binds
    @Singleton
    abstract fun bindSearchHistoryRepository(
        repositoryImpl: SearchHistoryRepositoryImpl
    ): SearchHistoryRepository
}