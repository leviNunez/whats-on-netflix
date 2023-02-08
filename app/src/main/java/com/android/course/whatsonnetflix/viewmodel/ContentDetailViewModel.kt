package com.android.course.whatsonnetflix.viewmodel

import androidx.lifecycle.*
import com.android.course.whatsonnetflix.domain.NetflixItemModel
import com.android.course.whatsonnetflix.domain.NetflixItemTitleType
import com.android.course.whatsonnetflix.domain.repository.NetflixContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val NETFLIX_ITEM = "netflixItem"
private const val TITLE_TV_SHOWS = "TV Shows"
private const val TITLE_MOVIES = "Movies"
private const val LIST_SIZE = 12

@HiltViewModel
class ContentDetailViewModel @Inject constructor(
    state: SavedStateHandle,
    private val repository: NetflixContentRepository
) : ViewModel() {

    private var _netflixItem = MutableStateFlow<NetflixItemModel?>(null)
    val netflixItem: StateFlow<NetflixItemModel?> = _netflixItem

    private var _moreLikeThis = MutableStateFlow<List<NetflixItemModel>?>(null)
    val moreLikeThis: StateFlow<List<NetflixItemModel>?> = _moreLikeThis

    init {
        state.get<NetflixItemModel>(NETFLIX_ITEM)?.let { selectedItem ->
            _netflixItem.value = selectedItem
            val title = getTitle(selectedItem.titleType)
            viewModelScope.launch {
                val category = repository.getCategoryByTitle(title)
                _moreLikeThis.value =
                    getFilteredList(toFilter = category.netflixItemList, toExclude = selectedItem)
            }
        }
    }

    private fun getTitle(titleType: String): String =
        when (titleType) {
            NetflixItemTitleType.TVSHOWS.value -> TITLE_TV_SHOWS
            else -> TITLE_MOVIES
        }

    private fun getFilteredList(
        toFilter: List<NetflixItemModel>,
        toExclude: NetflixItemModel
    ): List<NetflixItemModel> =
        toFilter.filter { it.netflixId != toExclude.netflixId }.shuffled().take(
            LIST_SIZE
        )

}

