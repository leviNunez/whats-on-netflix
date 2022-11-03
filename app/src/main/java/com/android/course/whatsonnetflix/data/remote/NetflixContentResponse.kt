package com.android.course.whatsonnetflix.data.remote

import com.android.course.whatsonnetflix.data.local.NetflixContentEntity
import com.android.course.whatsonnetflix.data.local.NetflixContentPreviewEntity
import com.android.course.whatsonnetflix.domain.NetflixContentPreview
import com.android.course.whatsonnetflix.utils.*
import com.squareup.moshi.*

@JsonClass(generateAdapter = true)
data class NetflixContentPreviewResponse(
    @Json(name = "Object") val metaData: Map<String, Int>,
    @NullToEmptyList
    @Json(name = "results") val contentPreviewList: List<NetworkNetflixContentPreview>
)

@JsonClass(generateAdapter = true)
data class NetworkNetflixContentPreview(
    @Json(name = "netflix_id") val netflixId: Long,
    val img: String,
    @Json(name = "title_type") val titleType: String,
    @Json(name = "title_date") val titleDate: String,
    val title: String,
    val synopsis: String,
    val rating: String,
    val year: String,
    val runtime: String,
    val imdb_id: String,
    val poster: String,
    val top250: Int,
    val top250tv: Int
)

fun NetflixContentPreviewResponse.asDatabaseModel(): List<NetflixContentPreviewEntity> {
    return contentPreviewList.map {
        NetflixContentPreviewEntity(
            netflixId = it.netflixId,
            img = it.img,
            title = it.title,
            titleType = it.titleType,
            titleDate = it.titleDate.convertToDate(),
        )
    }
}

fun NetflixContentPreviewResponse.asDomainModel(): List<NetflixContentPreview> {
    return contentPreviewList.map {
        NetflixContentPreview(
            netflixId = it.netflixId,
            img = it.img,
            title = it.title,
            titleType = it.titleType,
            titleDate = it.titleDate.convertToDate(),
        )
    }
}

@JsonClass(generateAdapter = true)
data class NetflixContentDetailResponse(
    @Json(name = "netflix_id") val netflixId: String,
    val title: String,
    @Json(name = "maturity_label") val maturityLabel: String,
    @Json(name = "large_image") val largeImage: String,
    @Json(name = "title_type") val titleType: String,
    val synopsis: String,
    val year: String,
    @Json(name = "alt_runtime") val altRuntime: String,
    @Json(name = "start_date") val startDate: String,
    val alt_id: String,
    val alt_image: String,
    val alt_metascore: String,
    val alt_plot: String,
    val alt_votes: String,
    val awards: String,
    val default_image: String,
    val latest_date: String,
    val maturity_level: String,
    val origin_country: String,
    val poster: String,
    val rating: String,
    val runtime: String,
)

fun NetflixContentDetailResponse.asDatabaseModel(): NetflixContentEntity =
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

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullToEmptyList

class NullToEmptyListAdapter {
    @ToJson
    fun toJson(@NullToEmptyList value: List<NetworkNetflixContentPreview>): List<NetworkNetflixContentPreview> {
        return value
    }

    @FromJson
    @NullToEmptyList
    fun fromJson(@javax.annotation.Nullable data: List<NetworkNetflixContentPreview>?): List<NetworkNetflixContentPreview> {
        return data ?: listOf()
    }
}