package com.android.course.whatsonnetflix.presentation.newcontent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.repository.ContentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewContentViewModel @Inject constructor(private val repository: ContentsRepository) :
    ViewModel() {

    private val _status = MutableLiveData<ContentApiStatus>()

    val status: LiveData<ContentApiStatus> get() = _status

    val newContents = repository.newContents

    init {
        getNewContent()
    }

    private fun getNewContent() {
        viewModelScope.launch {

            try {
                _status.value = ContentApiStatus.LOADING
                Timber.i("Status value: ${status.value}")
                repository.refreshNewContents()
                _status.value = ContentApiStatus.DONE
                Timber.i("Status value: ${status.value}")
            } catch (e: Exception) {
                _status.value = ContentApiStatus.ERROR
                Timber.i("Status value: ${status.value}")
                Timber.i("An error occurred: $e")
            }

        }
    }

}