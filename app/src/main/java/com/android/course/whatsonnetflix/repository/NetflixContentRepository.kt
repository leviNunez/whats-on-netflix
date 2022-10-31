package com.android.course.whatsonnetflix.repository

import androidx.lifecycle.LiveData
import com.android.course.whatsonnetflix.data.local.NetflixSearchHistoryEntity
import com.android.course.whatsonnetflix.domain.NetflixContent
import com.android.course.whatsonnetflix.domain.NetflixContentPreview
import com.android.course.whatsonnetflix.domain.NetflixSearchHistoryItem

interface NetflixContentRepository {
    val tvShows: LiveData<List<NetflixContentPreview>>
    val movies: LiveData<List<NetflixContentPreview>>
    val searchHistory: LiveData<List<NetflixSearchHistoryItem>>

    suspend fun refreshNetflixContent()
    suspend fun getNetflixContentDetail(contentId: Long)
    suspend fun getNetflixContentByTitle(contentTitle: String): List<NetflixContentPreview>
    suspend fun addNetflixSearchHistoryItem(searchHistoryItem: NetflixSearchHistoryItem)
    suspend fun deleteSearchHistoryItem(searchHistoryItem: NetflixSearchHistoryItem)
    suspend fun findNetflixContentById(contentId: Long): NetflixContent

}