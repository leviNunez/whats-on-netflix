package com.android.course.whatsonnetflix.data.remote

import com.android.course.whatsonnetflix.data.local.NetflixItemEntity
import com.android.course.whatsonnetflix.domain.NetflixItemModel
import com.android.course.whatsonnetflix.domain.RegionModel
import com.android.course.whatsonnetflix.utils.*
import com.squareup.moshi.*

@JsonClass(generateAdapter = true)
data class NetworkNetflixItemContainer(
    @Json(name = "Object") val metaData: Map<String, Int>,
    @NullToEmptyList
    @Json(name = "results") val items: List<NetworkNetflixItem>
)

@JsonClass(generateAdapter = true)
data class NetworkNetflixItem(
    @Json(name = "netflix_id") val netflixId: Long,
    @Json(name = "img") val thumbnail: String,
    @Json(name = "title_type") val titleType: String,
    @Json(name = "title_date") val streamingDate: String,
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

fun NetworkNetflixItemContainer.asDatabaseModel(isExpiring: Boolean = false): List<NetflixItemEntity> {
    return items.map {
        NetflixItemEntity(
            netflixId = it.netflixId,
            title = it.title.decodeHtmlEntities(),
            thumbnail = it.thumbnail,
            poster = it.poster,
            titleType = it.titleType,
            synopsis = it.synopsis.decodeHtmlEntities(),
            year = it.year,
            runtime = it.runtime,
            streamingDate = it.streamingDate.convertToDate(),
            isExpiring = isExpiring
        )
    }
}

fun NetworkNetflixItemContainer.asDomainModel(isExpiring: Boolean = false): List<NetflixItemModel> =
    items.map {
        NetflixItemModel(
            netflixId = it.netflixId,
            title = it.title.decodeHtmlEntities(),
            thumbnail = it.thumbnail,
            poster = it.poster,
            titleType = it.titleType,
            synopsis = it.synopsis.decodeHtmlEntities(),
            year = it.year,
            runtime = it.runtime,
            streamingDate = it.streamingDate.convertToDate(),
            isExpiring = isExpiring
        )
    }


@JsonClass(generateAdapter = true)
data class NetworkRegionItemContainer(
    @Json(name = "results") val items: List<NetworkRegionItem>
)

@JsonClass(generateAdapter = true)
data class NetworkRegionItem(
    val id: Int,
    val country: String,
    val countrycode: String,
    val expiring: Int,
    val nl7: Int,
    val tvids: Int,
    val tmovs: Int,
    val tseries: Int,
)

fun NetworkRegionItemContainer.asDomainModel(): List<RegionModel> =
    items.map {
        RegionModel(
            id = it.id,
            country = it.country,
            countryCode = it.countrycode
        )
    }

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullToEmptyList

class NullToEmptyListAdapter {
    @ToJson
    fun toJson(@NullToEmptyList value: List<NetworkNetflixItem>): List<NetworkNetflixItem> {
        return value
    }

    @FromJson
    @NullToEmptyList
    fun fromJson(@javax.annotation.Nullable data: List<NetworkNetflixItem>?): List<NetworkNetflixItem> {
        return data ?: listOf()
    }
}