package com.android.course.whatsonnetflix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.course.whatsonnetflix.domain.SearchHistoryModel
import com.android.course.whatsonnetflix.domain.repository.SearchHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchableViewModel @Inject constructor(private val searchHistoryRepository: SearchHistoryRepository) :
    ViewModel() {

    fun getSearchHistory() = searchHistoryRepository.searchHistory

    fun addQueryToSearchHistory(query: String) {
        viewModelScope.launch { searchHistoryRepository.saveSearchHistory(query) }
    }

    fun deleteItem(item: SearchHistoryModel) {
        viewModelScope.launch { searchHistoryRepository.deleteSearchHistory(item.id) }
    }
}



