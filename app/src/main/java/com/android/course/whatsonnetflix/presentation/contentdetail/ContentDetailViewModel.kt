package com.android.course.whatsonnetflix.presentation.contentdetail

import androidx.lifecycle.*
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.domain.NetflixContent
import com.android.course.whatsonnetflix.repository.NetflixContentRepository
import com.android.course.whatsonnetflix.utils.NoDataException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContentDetailViewModel @Inject constructor(
    private val repository: NetflixContentRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _status = MutableLiveData<ContentApiStatus>()
    val status: LiveData<ContentApiStatus> get() = _status

    private val _netflixContent = MutableLiveData<NetflixContent>()
    val netflixContent: LiveData<NetflixContent> get() = _netflixContent

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
                    repository.getNetflixContentDetail(it)
                    _status.value = ContentApiStatus.DONE
                } catch (e: HttpException) {
                    _status.value = ContentApiStatus.ERROR
                    _showToastEvent.value = true
                } finally {
                    getNetflixContentFromDatabase(it)
                }
            }
        }
    }

    private suspend fun getNetflixContentFromDatabase(contentId: Long) {
        try {
            _netflixContent.value = repository.findNetflixContentById(contentId)
        } catch (e: NoDataException) {
            Timber.i("Database content could not be found.")
        }
    }

    fun doneShowingToast() {
        _showToastEvent.value = false
    }

}