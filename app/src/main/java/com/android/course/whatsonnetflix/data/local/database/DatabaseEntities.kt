package com.android.course.whatsonnetflix.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.course.whatsonnetflix.domain.NewContent

@Entity
data class DatabaseNewContent(
    @PrimaryKey
    val netflixId: Long,
    val title: String,
    val img: String,
    val titleType: String,
    val synopsis: String,
    val rating: String,
    val year: String,
    val runtime: String,
    val poster: String,
    val titleDate: String
)

fun List<DatabaseNewContent>.asDomainModel(): List<NewContent> {
    return map {
        NewContent(
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
    }
}
