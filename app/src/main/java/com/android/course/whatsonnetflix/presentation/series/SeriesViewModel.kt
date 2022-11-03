package com.android.course.whatsonnetflix.presentation.series

import androidx.lifecycle.*
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.domain.repository.NetflixContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val repository: NetflixContentRepository) :
    ViewModel() {

    val series = repository.series

    private val _status = MutableLiveData<ContentApiStatus>()
    val status: LiveData<ContentApiStatus> get() = _status

    private val _navigateToSelectedContent = MutableLiveData<Long?>()
    val navigateToSelectedContent: LiveData<Long?> get() = _navigateToSelectedContent

    private val _showToastEvent = MutableLiveData<Boolean>()
    val showToastEvent: LiveData<Boolean> get() = _showToastEvent

    init {
        refreshContent()
    }

    fun refreshContent() {
        Timber.i("Refreshing content...")
        viewModelScope.launch {
            try {
                _status.value = ContentApiStatus.LOADING
                repository.refreshNetflixContent(TITLE_TYPE)
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

private const val TITLE_TYPE = "series"


