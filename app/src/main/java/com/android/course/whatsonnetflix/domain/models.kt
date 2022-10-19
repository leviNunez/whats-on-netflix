package com.android.course.whatsonnetflix.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class NewContent(
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
) : Parcelable

data class DeletedContent(
    val netflix_id: Long,
    val country_id: Int,
    val title: String,
    val delete_date: String,
    val country_code: String

)
