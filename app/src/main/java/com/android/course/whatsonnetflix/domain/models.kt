package com.android.course.whatsonnetflix.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Date


enum class NetflixItemTitleType(val value: String) {
    TVSHOWS("series"),
    MOVIES("movie")
}


data class CategoryModel(
    val id: Int,
    val title: String,
    val netflixItemList: List<NetflixItemModel>
)


@Parcelize
data class NetflixItemModel(
    val netflixId: Long,
    val title: String,
    val thumbnail: String,
    val poster: String,
    val titleType: String,
    val synopsis: String,
    val year: String,
    val runtime: String,
    val streamingDate: Date,
    val isExpiring: Boolean
) : Parcelable


data class RegionModel(
    val id: Int,
    val country: String,
    val countryCode: String,
)

data class SearchHistoryModel(
    val id: Long,
    val searchTerm: String
)








