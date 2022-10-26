package com.android.course.whatsonnetflix.repository

import androidx.lifecycle.LiveData
import com.android.course.whatsonnetflix.domain.NetflixContent
import com.android.course.whatsonnetflix.domain.NetflixContentPreview

interface ContentRepository {
    val tvShows: LiveData<List<NetflixContentPreview>>
    val movies: LiveData<List<NetflixContentPreview>>

    suspend fun refreshContent()
    suspend fun getContentDetail(contentId: Long)
    suspend fun findContentById(contentId: Long): NetflixContent

}