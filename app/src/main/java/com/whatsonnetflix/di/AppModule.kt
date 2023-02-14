package com.whatsonnetflix.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.whatsonnetflix.data.local.NetflixContentDao
import com.whatsonnetflix.data.local.NetflixContentDatabase
import com.whatsonnetflix.data.local.SearchHistoryDao
import com.whatsonnetflix.data.remote.ContentsApi
import com.whatsonnetflix.data.remote.NetworkInterceptor
import com.whatsonnetflix.data.remote.NullToEmptyListAdapter
import com.whatsonnetflix.utils.PrefConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providePrefConfig(app: Application): PrefConfig {
        return PrefConfig(app)
    }

    @Provides
    @Singleton
    fun provideNetflixContentDatabase(@ApplicationContext appContext: Context): NetflixContentDatabase {
        return Room.databaseBuilder(
            appContext,
            NetflixContentDatabase::class.java,
            "netflixItem"
        ).build()
    }

    @Provides
    fun provideNetflixContentDao(netflixContentDatabase: NetflixContentDatabase): NetflixContentDao {
        return netflixContentDatabase.netflixContentDao()
    }

    @Provides
    fun provideSearchHistoryDao(netflixContentDatabase: NetflixContentDatabase): SearchHistoryDao {
        return netflixContentDatabase.searchHistoryDao()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(NullToEmptyListAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(NetworkInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideContentsApi(okHttpClient: OkHttpClient, moshi: Moshi): ContentsApi {
        return Retrofit.Builder()
            .baseUrl("https://unogs-unogs-v1.p.rapidapi.com/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ContentsApi::class.java)
    }
}