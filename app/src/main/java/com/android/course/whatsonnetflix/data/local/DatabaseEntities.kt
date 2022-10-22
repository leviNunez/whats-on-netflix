package com.android.course.whatsonnetflix.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.course.whatsonnetflix.domain.Content
import com.android.course.whatsonnetflix.domain.ContentPreview

@Entity(tableName = "content_preview_table")
data class DatabaseContentPreview(
    @PrimaryKey
    val netflixId: Long,
    val img: String,
    val titleType: String,
)


fun List<DatabaseContentPreview>.asDomainModel(): List<ContentPreview> {
    return map {
        ContentPreview(
            netflixId = it.netflixId,
            img = it.img,
            titleType = it.titleType,
        )
    }
}

@Entity(tableName = "content_detail_table")
data class DatabaseContent(
    @PrimaryKey
    val netflixId: Long,
    val title: String,
    val maturityLabel: String,
    val img: String,
    val titleType: String,
    val synopsis: String,
    val year: String,
    val runtime: String,
    val titleDate: String
)

fun DatabaseContent.asDomainModel(): Content = Content(
    netflixId = netflixId,
    title = title,
    maturityLabel = maturityLabel,
    img = img,
    titleType = titleType,
    synopsis = synopsis,
    year = year,
    runtime = runtime,
    titleDate = titleDate
)




