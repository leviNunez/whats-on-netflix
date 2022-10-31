package com.android.course.whatsonnetflix.data.remote

import com.android.course.whatsonnetflix.domain.NetflixContentPreview
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


enum class ContentApiStatus { LOADING, ERROR, DONE }

/*
    78 is the Netflix's id for the United States.
 */
private const val DEFAULT_COUNTRY = "78"

interface ContentsApi {

    @GET("search/titles?order_by=date&country_list=$DEFAULT_COUNTRY")
    suspend fun getNetflixContent(): Response<NetflixContentPreviewResponse>

    @GET("title/details")
    suspend fun getNetflixContentDetail(
        @Query("netflix_id") type: Long
    ): Response<NetflixContentDetailResponse>

    @GET("search/titles?order_by=title&country_list=$DEFAULT_COUNTRY")
    suspend fun getNetflixContentByTitle(
        @Query("title") type: String
    ): Response<NetflixContentPreviewResponse>


}