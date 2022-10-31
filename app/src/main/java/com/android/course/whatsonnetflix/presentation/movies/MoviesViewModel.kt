package com.android.course.whatsonnetflix.presentation.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.course.whatsonnetflix.repository.NetflixContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(repository: NetflixContentRepository) :
    ViewModel() {

    val movies = repository.movies

    private val _navigateToSelectedContent = MutableLiveData<Long?>()
    val navigateToSelectedContent: LiveData<Long?> get() = _navigateToSelectedContent

    fun displayContentDetails(contentId: Long) {
        _navigateToSelectedContent.value = contentId
    }

    fun doneNavigating() {
        _navigateToSelectedContent.value = null
    }


}