package com.android.course.whatsonnetflix.domain

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
    val titleType: String,
    val titleDate: Date
)

