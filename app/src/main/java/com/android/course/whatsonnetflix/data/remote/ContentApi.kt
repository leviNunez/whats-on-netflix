package com.android.course.whatsonnetflix.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


enum class ContentApiStatus { LOADING, ERROR, DONE }

interface ContentsApi {

    @GET("search/titles?order_by=date")
    suspend fun getNewContent(): Response<ContentResponse>

    @GET("title/details")
    suspend fun getContentDetail(@Query("netflix_id") type: Long): Response<ContentDetailResponse>

    @GET("search/deleted")
    suspend fun getDeletedContents()

}