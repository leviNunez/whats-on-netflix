package com.whatsonnetflix.di

import com.whatsonnetflix.data.repository.NetflixContentRepositoryImpl
import com.whatsonnetflix.data.repository.SearchHistoryRepositoryImpl
import com.whatsonnetflix.domain.repository.NetflixContentRepository
import com.whatsonnetflix.domain.repository.SearchHistoryRepository
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