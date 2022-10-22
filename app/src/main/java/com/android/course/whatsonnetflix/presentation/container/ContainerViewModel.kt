package com.android.course.whatsonnetflix.presentation.container

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
class ContainerViewModel @Inject constructor(private val repository: ContentRepository) :
    ViewModel() {

    private val _status = MutableLiveData<ContentApiStatus>()
    val status: LiveData<ContentApiStatus> get() = _status

    private val _showToastEvent = MutableLiveData<Boolean>()
    val showToastEvent: LiveData<Boolean> get() = _showToastEvent

    init {
        getContent()
    }

    private fun getContent() {
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

    fun doneShowingToast() {
        _showToastEvent.value = false
    }
}