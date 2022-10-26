package com.android.course.whatsonnetflix.di

import com.android.course.whatsonnetflix.data.repository.ContentRepositoryImpl
import com.android.course.whatsonnetflix.repository.ContentRepository
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
    abstract fun bindContentRepository(
        contentsRepositoryImpl: ContentRepositoryImpl
    ): ContentRepository
}