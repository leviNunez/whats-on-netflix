package com.android.course.whatsonnetflix.data.repository

import com.android.course.whatsonnetflix.data.local.SearchHistoryDao
import com.android.course.whatsonnetflix.data.local.SearchHistoryEntity
import com.android.course.whatsonnetflix.data.local.asDomainModel
import com.android.course.whatsonnetflix.domain.SearchHistoryModel
import com.android.course.whatsonnetflix.domain.repository.SearchHistoryRepository
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