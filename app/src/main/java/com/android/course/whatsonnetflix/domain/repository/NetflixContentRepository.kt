package com.android.course.whatsonnetflix.domain.repository

import androidx.lifecycle.LiveData
import com.android.course.whatsonnetflix.domain.NetflixContent
import com.android.course.whatsonnetflix.domain.NetflixContentPreview
import com.android.course.whatsonnetflix.domain.NetflixSearchHistoryItem

interface NetflixContentRepository {
    val series: LiveData<List<NetflixContentPreview>>
    val movies: LiveData<List<NetflixContentPreview>>
    val searchHistory: LiveData<List<NetflixSearchHistoryItem>>

    suspend fun refreshNetflixContent(titleType: String)

    suspend fun getNetflixContentDetail(contentId: Long)

    suspend fun getNetflixContentByTitle(contentTitle: String): List<NetflixContentPreview>

    suspend fun addNetflixSearchHistoryItemToDb(netflixSearchHistoryItem: NetflixSearchHistoryItem)

    suspend fun deleteSearchHistoryItem(searchHistoryItem: NetflixSearchHistoryItem)

    suspend fun findNetflixContentById(contentId: Long): NetflixContent

}