package com.android.course.whatsonnetflix.repository

import androidx.lifecycle.LiveData
import com.android.course.whatsonnetflix.domain.Content
import com.android.course.whatsonnetflix.domain.ContentPreview

interface ContentRepository {
    val tvShows: LiveData<List<ContentPreview>>
    val movies: LiveData<List<ContentPreview>>

    suspend fun refreshContent()
    suspend fun getContentDetail(contentId: Long)
    suspend fun findContentById(contentId: Long): Content

}