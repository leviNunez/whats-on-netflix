package com.android.course.whatsonnetflix.di

import android.content.Context
import androidx.room.Room
import com.android.course.whatsonnetflix.data.local.database.ContentDao
import com.android.course.whatsonnetflix.data.local.database.ContentDatabase
import com.android.course.whatsonnetflix.data.remote.ContentsApi
import com.android.course.whatsonnetflix.data.remote.moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContentDatabase(@ApplicationContext appContext: Context): ContentDatabase {
        return Room.databaseBuilder(
            appContext,
            ContentDatabase::class.java,
            "contents"
        ).build()
    }

    @Provides
    fun provideChannelDao(contentDatabase: ContentDatabase): ContentDao {
        return contentDatabase.contentDao()
    }

    @Singleton
    @Provides
    fun provideConverterFactory() = MoshiConverterFactory.create()


    @Provides
    @Singleton
    fun provideContentsApi(): ContentsApi {
        return Retrofit.Builder()
            .baseUrl("https://unogs-unogs-v1.p.rapidapi.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ContentsApi::class.java)
    }
}