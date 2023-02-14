package com.whatsonnetflix.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.whatsonnetflix.utils.PrefConfig
import com.whatsonnetflix.R
import com.whatsonnetflix.data.remote.Result
import com.whatsonnetflix.domain.CategoryModel
import com.whatsonnetflix.domain.repository.NetflixContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NetflixContentRepository,
    private val prefConfig: PrefConfig,
    private val app: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private lateinit var cachedRegionCode: String

    fun getCategories() = repository.categories

    init {
        loadRegionAndRefreshCategories()
    }

    fun loadRegionAndRefreshCategories() {
        _uiState.update { it.copy(hasNoRegion = false) }
        val defaultValue = app.getString(R.string.default_value)
        cachedRegionCode = prefConfig.loadRegionCode() ?: defaultValue
        if (cachedRegionCode == defaultValue) {
            _uiState.update { it.copy(hasNoRegion = true) }
        } else {
            refreshCategories()
        }
    }

    fun onRetry() {
        refreshCategories()
    }

    private fun refreshCategories() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = repository.refreshAllCategories(regionCode = cachedRegionCode)
            _uiState.update {
                when (result) {
                    is Result.Success -> {
                        it.copy(isLoading = false, hasError = false)
                    }
                    is Result.Error -> {
                        it.copy(isLoading = false, hasError = true)
                    }
                }
            }
        }
    }

    fun onListCollected(list: List<CategoryModel>) {
        _uiState.update {
            if (list.isEmpty() and uiState.value.hasError) {
                it.copy(shouldShowRetryLayout = true, shouldShowCategories = false)
            } else {
                it.copy(shouldShowRetryLayout = false, shouldShowCategories = true)
            }
        }
    }


    fun checkRegion() {
        val currentRegionCode = prefConfig.loadRegionCode()
        currentRegionCode?.let {
            if (it != cachedRegionCode) {
                cachedRegionCode = it
                refreshCategories()
            }
        }
    }

    data class HomeUiState(
        val isLoading: Boolean = false,
        val hasNoRegion: Boolean = false,
        val hasError: Boolean = false,
        val shouldShowCategories: Boolean = false,
        val shouldShowRetryLayout: Boolean = false
    )
}


