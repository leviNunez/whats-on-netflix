package com.android.course.whatsonnetflix.presentation.contentdetail

import androidx.lifecycle.*
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.domain.Content
import com.android.course.whatsonnetflix.repository.ContentRepository
import com.android.course.whatsonnetflix.utils.NoDataException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContentDetailViewModel @Inject constructor(
    private val repository: ContentRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _status = MutableLiveData<ContentApiStatus>()
    val status: LiveData<ContentApiStatus> get() = _status

    private val _content = MutableLiveData<Content>()
    val content: LiveData<Content> get() = _content

    private val _showToastEvent = MutableLiveData<Boolean>()
    val showToastEvent: LiveData<Boolean> get() = _showToastEvent

    init {
        getContentDetail()
    }

    private fun getContentDetail() {
        viewModelScope.launch {
            state.get<Long>("contentId")?.let {
                try {
                    _status.value = ContentApiStatus.LOADING
                    repository.getContentDetail(it)
                    _status.value = ContentApiStatus.DONE
                } catch (e: HttpException) {
                    _status.value = ContentApiStatus.ERROR
                    _showToastEvent.value = true
                } finally {
                    getContentFromDatabse(it)
                }
            }
        }
    }

    private suspend fun getContentFromDatabse(contentId: Long) {
        try {
            _content.value = repository.findContentById(contentId)
            Timber.i("${_content.value}")
        } catch (e: NoDataException) {
            Timber.i("BOBO no hay datos en la base de datos")
        }
    }

    fun doneShowingToast() {
        _showToastEvent.value = false
    }

}