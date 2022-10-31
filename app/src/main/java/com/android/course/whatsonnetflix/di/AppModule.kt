package com.android.course.whatsonnetflix.di

import android.content.Context
import androidx.room.Room
import com.android.course.whatsonnetflix.data.local.NetflixContentDao
import com.android.course.whatsonnetflix.data.local.NetflixContentDatabase
import com.android.course.whatsonnetflix.data.remote.ContentsApi
import com.android.course.whatsonnetflix.data.remote.NetworkInterceptor
import com.android.course.whatsonnetflix.data.remote.NullToEmptyListAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    @Singleton
    fun provideContentDatabase(@ApplicationContext appContext: Context): NetflixContentDatabase {
        return Room.databaseBuilder(
            appContext,
            NetflixContentDatabase::class.java,
            "netflixContent"
        ).build()
    }

    @Provides
    fun provideContentDao(netflixContentDatabase: NetflixContentDatabase): NetflixContentDao {
        return netflixContentDatabase.contentDao()
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