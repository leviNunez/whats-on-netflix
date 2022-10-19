package com.android.course.whatsonnetflix.data.remote


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

enum class ContentApiStatus { LOADING, ERROR, DONE }

interface ContentsApi {
    @Headers(
        "X-RapidAPI-Key: 70c53275f4msh676abc3ec64f40dp1e5bccjsn507899abc973",
        "X-RapidAPI-Host: unogs-unogs-v1.p.rapidapi.com"
    )
    @GET("search/titles?order_by=date")
    suspend fun getNewContents(): NewContentResponse

    @Headers(
        "X-RapidAPI-Key: 70c53275f4msh676abc3ec64f40dp1e5bccjsn507899abc973",
        "X-RapidAPI-Host: unogs-unogs-v1.p.rapidapi.com"
    )
    @GET("search/deleted")
    suspend fun getDeletedContents()

}