package com.android.course.whatsonnetflix.presentation.tvshows

import androidx.lifecycle.*
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(repository: ContentRepository) :
    ViewModel() {


    private val _navigateToSelectedContent = MutableLiveData<Long?>()
    val navigateToSelectedContent: LiveData<Long?> get() = _navigateToSelectedContent

    val tvShows = repository.tvShows



    fun displayContentDetails(contentId: Long) {
        _navigateToSelectedContent.value = contentId
    }

    fun doneNavigating() {
        _navigateToSelectedContent.value = null
    }


}