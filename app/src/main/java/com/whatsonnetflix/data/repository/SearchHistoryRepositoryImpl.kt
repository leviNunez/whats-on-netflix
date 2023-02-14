package com.whatsonnetflix.data.repository

import com.whatsonnetflix.data.local.SearchHistoryDao
import com.whatsonnetflix.data.local.SearchHistoryEntity
import com.whatsonnetflix.data.local.asDomainModel
import com.whatsonnetflix.domain.SearchHistoryModel
import com.whatsonnetflix.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao,
) : SearchHistoryRepository {
    override val searchHistory: Flow<List<SearchHistoryModel>> =
        searchHistoryDao.getAllSearchHistory().map {
            it.asDomainModel()
        }

    override suspend fun saveSearchHistory(searchTerm: String) {
        val toSave =
            SearchHistoryEntity(searchTerm = searchTerm, timeStamp = System.currentTimeMillis())
        searchHistoryDao.insertSearchHistory(searchHistory = toSave)
    }

    override suspend fun deleteSearchHistory(id: Long) {
        searchHistoryDao.deleteSearchHistoryById(id)
    }
}