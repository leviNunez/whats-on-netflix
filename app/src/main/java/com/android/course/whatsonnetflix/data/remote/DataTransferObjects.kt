package com.android.course.whatsonnetflix.data.remote

import com.android.course.whatsonnetflix.data.local.NetflixContentEntity
import com.android.course.whatsonnetflix.data.local.NetflixContentPreviewEntity
import com.android.course.whatsonnetflix.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContentResponse(
    @Json(name = "Object") val metaData: Map<String, Int>,
    @Json(name = "results") val contentPreviewList: List<NetworkContentPreview>
)

@JsonClass(generateAdapter = true)
data class NetworkContentPreview(
    @Json(name = "netflix_id") val netflixId: Long,
    val title: String,
    val img: String,
    @Json(name = "title_type") val titleType: String,
    val synopsis: String,
    val rating: String,
    val year: String,
    val runtime: String,
    @Json(name = "imdb_id") val imdbId: String,
    val poster: String,
    val top250: Int,
    val top250tv: Int,
    @Json(name = "title_date") val titleDate: String
)

fun ContentResponse.asDatabaseModel(): Array<NetflixContentPreviewEntity> {
    return contentPreviewList.map {
        NetflixContentPreviewEntity(
            netflixId = it.netflixId,
            img = it.img,
            titleType = it.titleType,
            titleDate = it.titleDate.convertToDate(),
        )
    }.toTypedArray()
}


@JsonClass(generateAdapter = true)
data class ContentDetailResponse(
    @Json(name = "alt_id") val altId: String,
    @Json(name = "alt_image") val altImage: String,
    @Json(name = "alt_metascore") val altMetascore: String,
    @Json(name = "alt_plot") val altPlot: String,
    @Json(name = "alt_runtime") val altRuntime: String,
    @Json(name = "alt_votes") val altVotes: String,
    val awards: String,
    @Json(name = "default_image") val defaultImage: String,
    @Json(name = "large_image") val largeImage: String,
    @Json(name = "latest_date") val latestDate: String,
    @Json(name = "maturity_label") val maturityLabel: String,
    @Json(name = "maturity_level") val maturityLevel: String,
    @Json(name = "netflix_id") val netflixId: String,
    @Json(name = "origin_country") val originCountry: String,
    val poster: String,
    val rating: String,
    val runtime: String,
    @Json(name = "start_date") val startDate: String,
    val synopsis: String,
    val title: String,
    @Json(name = "title_type") val titleType: String,
    val year: String
)

fun ContentDetailResponse.asDatabaseModel(): NetflixContentEntity =
    NetflixContentEntity(
        netflixId = netflixId.toLong(),
        title = title.decodeHtmlEntities(),
        maturityLabel = maturityLabel,
        img = largeImage,
        titleType = appendParenthesis(titleType),
        synopsis = synopsis.decodeHtmlEntities(),
        year = year,
        runtime = altRuntime.convertSecondsToTime(),
        titleDate = startDate.convertToDate()
    )

