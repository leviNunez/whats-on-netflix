package com.whatsonnetflix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whatsonnetflix.domain.SearchHistoryModel
import com.whatsonnetflix.domain.repository.SearchHistoryRepository
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



