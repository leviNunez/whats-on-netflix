package com.android.course.whatsonnetflix.presentation.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: ContentRepository) :
    ViewModel() {

    private val _status = MutableLiveData<ContentApiStatus>()
    val status: LiveData<ContentApiStatus> get() = _status

    private val _navigateToSelectedContent = MutableLiveData<Long?>()
    val navigateToSelectedContent: LiveData<Long?> get() = _navigateToSelectedContent

    private val _showToastEvent = MutableLiveData<Boolean>()
    val showToastEvent: LiveData<Boolean> get() = _showToastEvent

    val movies = repository.movies

    init {
        getNewContent()
    }

    private fun getNewContent() {
        viewModelScope.launch {
            try {
                _status.value = ContentApiStatus.LOADING
                repository.refreshContent()
                _status.value = ContentApiStatus.DONE
            } catch (e: HttpException) {
                _status.value = ContentApiStatus.ERROR
                _showToastEvent.value = true
            }
        }
    }

    fun displayContentDetails(contentId: Long) {
        _navigateToSelectedContent.value = contentId
    }

    fun doneNavigating() {
        _navigateToSelectedContent.value = null
    }

    fun doneShowingToast() {
        _showToastEvent.value = false
    }

}