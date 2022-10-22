package com.android.course.whatsonnetflix.domain


data class Content(
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

data class ContentPreview(
    val netflixId: Long,
    val img: String,
    val titleType: String,
)

