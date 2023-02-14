package com.whatsonnetflix.domain.repository

import com.whatsonnetflix.domain.SearchHistoryModel
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    val searchHistory: Flow<List<SearchHistoryModel>>

    suspend fun saveSearchHistory(searchTerm: String)

    suspend fun deleteSearchHistory(id: Long)
}