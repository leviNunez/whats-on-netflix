package com.android.course.whatsonnetflix.presentation.tvshows

import androidx.lifecycle.*
import com.android.course.whatsonnetflix.repository.NetflixContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(repository: NetflixContentRepository) :
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