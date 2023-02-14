package com.whatsonnetflix.data.remote

import retrofit2.http.GET
import retrofit2.http.Query


/*
    78 is Netflix's id for the United States.
 */
private const val DEFAULT_COUNTRY = "78"

interface ContentsApi {

    @GET("search/titles?order_by=date")
    suspend fun getNetflixContent(@Query("regionCode") type: String): NetworkNetflixItemContainer

    @GET("search/titles?order_by=date&expiring=true")
    suspend fun getExpiringNetflixContent(@Query("regionCode") type: String): NetworkNetflixItemContainer

    @GET("search/titles?order_by=date_asc&country_list=$DEFAULT_COUNTRY")
    suspend fun getNetflixContentByTitle(
        @Query("title") type: String
    ): NetworkNetflixItemContainer

    @GET("static/countries")
    suspend fun getRegions(): NetworkRegionItemContainer

}