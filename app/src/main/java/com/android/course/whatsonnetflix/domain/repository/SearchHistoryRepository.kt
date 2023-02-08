package com.android.course.whatsonnetflix.domain.repository

import com.android.course.whatsonnetflix.data.remote.Result
import com.android.course.whatsonnetflix.domain.NetflixItemModel
import com.android.course.whatsonnetflix.domain.SearchHistoryModel
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    val searchHistory: Flow<List<SearchHistoryModel>>

    suspend fun saveSearchHistory(searchTerm: String)

    suspend fun deleteSearchHistory(id: Long)
}