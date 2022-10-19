package com.android.course.whatsonnetflix.data.remote

import com.android.course.whatsonnetflix.data.local.database.DatabaseNewContent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewContentResponse(
    val Object: Map<String, Int>,
    val results: List<NetworkNewContent>
)

@JsonClass(generateAdapter = true)
data class NetworkNewContent(
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

fun NewContentResponse.asDatabaseModel(): Array<DatabaseNewContent> {
    return results.map {
        DatabaseNewContent(
            netflixId = it.netflixId,
            title = it.title,
            img = it.img,
            titleType = it.titleType,
            synopsis = it.synopsis,
            rating = it.rating,
            year = it.year,
            runtime = it.runtime,
            poster = it.poster,
            titleDate = it.titleDate
        )
    }.toTypedArray()
}
