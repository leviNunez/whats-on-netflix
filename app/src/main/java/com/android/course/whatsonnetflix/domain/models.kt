package com.android.course.whatsonnetflix.domain

import com.android.course.whatsonnetflix.data.local.NetflixSearchHistoryEntity
import com.android.course.whatsonnetflix.utils.decodeHtmlEntities
import java.util.*

data class NetflixContent(
    val netflixId: Long,
    val title: String,
    val maturityLabel: String,
    val img: String,
    val titleType: String,
    val synopsis: String,
    val year: String,
    val runtime: String,
    val titleDate: Date
)

data class NetflixContentPreview(
    val netflixId: Long,
    val img: String,
    val title: String,
    val titleType: String,
    val titleDate: Date
)

fun NetflixContentPreview.asNetflixSearchHistoryItem(): NetflixSearchHistoryItem =
    NetflixSearchHistoryItem(
        netflixId = netflixId,
        img = img,
        title = title.decodeHtmlEntities()
    )

data class NetflixSearchHistoryItem(
    val netflixId: Long,
    val img: String,
    val title: String,
)

fun NetflixSearchHistoryItem.asDatabaseModel(): NetflixSearchHistoryEntity =
    NetflixSearchHistoryEntity(
        netflixId = netflixId,
        img = img,
        title = title,
        timestamp = System.currentTimeMillis()
    )






