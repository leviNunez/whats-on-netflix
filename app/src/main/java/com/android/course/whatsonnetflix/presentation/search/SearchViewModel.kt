package com.android.course.whatsonnetflix.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.domain.NetflixContentPreview
import com.android.course.whatsonnetflix.domain.NetflixSearchHistoryItem
import com.android.course.whatsonnetflix.domain.asNetflixSearchHistoryItem
import com.android.course.whatsonnetflix.repository.NetflixContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NetflixContentRepository) :
    ViewModel() {

    private var job = Job()
        get() {
            if (field.isCancelled) field = Job()
            return field
        }

    val searchHistory = repository.searchHistory

    private val _status = MutableLiveData<ContentApiStatus>()
    val status: LiveData<ContentApiStatus> get() = _status

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() = _searchQuery

    private val _searchResults = MutableLiveData<List<NetflixContentPreview>?>()
    val searchResults: LiveData<List<NetflixContentPreview>?> get() = _searchResults

    private val _navigateToSelectedContent = MutableLiveData<Long?>()
    val navigateToSelectedContent: LiveData<Long?> get() = _navigateToSelectedContent


    fun handleSearch(query: String) {
        job.cancel()
        _searchQuery.value = query
        when {
            query.isNotEmpty() -> searchNetflixContentByTitle(query)
            else -> {
                clearSearchResults()
                _status.value = ContentApiStatus.DONE
            }
        }
    }

    private fun searchNetflixContentByTitle(contentTitle: String) {
        viewModelScope.launch(job) {
            try {
                _status.value = ContentApiStatus.LOADING
                delay(100)
                _searchResults.value = repository.getNetflixContentByTitle(contentTitle)
                _status.value = ContentApiStatus.DONE
            } catch (e: HttpException) {
                _status.value = ContentApiStatus.ERROR
            }
        }
    }

    private fun clearSearchResults() {
        _searchResults.value = null
    }

    fun displaySearchHistoryItemDetails(contentId: Long) {
        _navigateToSelectedContent.value = contentId
    }

    fun displayNetflixContentPreviewDetails(netflixContentPreview: NetflixContentPreview) {
        _navigateToSelectedContent.value = netflixContentPreview.netflixId
        addSearchHistoryItemToDatabase(netflixContentPreview.asNetflixSearchHistoryItem())

    }

    private fun addSearchHistoryItemToDatabase(item: NetflixSearchHistoryItem) {
        viewModelScope.launch {
            repository.addNetflixSearchHistoryItem(item)
        }
    }

    fun deleteSearchHistoryItemFromDatabase(item: NetflixSearchHistoryItem) {
        viewModelScope.launch {
            repository.deleteSearchHistoryItem(item)
        }
    }

    fun doneNavigating() {
        _navigateToSelectedContent.value = null
    }

}