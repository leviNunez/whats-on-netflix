package com.android.course.whatsonnetflix.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.course.whatsonnetflix.data.remote.Result
import com.android.course.whatsonnetflix.domain.NetflixItemModel
import com.android.course.whatsonnetflix.domain.repository.NetflixContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH_TERM = "searchTerm"

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val repository: NetflixContentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchResultsUiState())
    val uiState: StateFlow<SearchResultsUiState> = _uiState

    init {
        val title = state.get<String>(SEARCH_TERM) ?: ""
        if (title.isNotEmpty()) {
            _uiState.update { it.copy(searchTerm = title) }
            searchByTitle(title)
        }
    }

    private fun searchByTitle(title: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = repository.getContentByTitle(title)
            _uiState.update {
                when (result) {
                    is Result.Success -> {
                        it.copy(isLoading = false, results = result.data)
                    }
                    is Result.Error -> {
                        it.copy(isLoading = false, hasError = true)
                    }
                }
            }
        }
    }

    fun onRetry() {
        _uiState.update { it.copy(hasError = false) }
        val title = uiState.value.searchTerm
        searchByTitle(title)
    }

    data class SearchResultsUiState(
        val isLoading: Boolean = false,
        val searchTerm: String = "",
        val results: List<NetflixItemModel>? = null,
        val hasError: Boolean = false
    )
}