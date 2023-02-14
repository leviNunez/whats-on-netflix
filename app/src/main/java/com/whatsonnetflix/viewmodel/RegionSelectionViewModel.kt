package com.whatsonnetflix.viewmodel

import androidx.lifecycle.*
import com.whatsonnetflix.utils.PrefConfig
import com.whatsonnetflix.data.remote.Result
import com.whatsonnetflix.domain.RegionModel
import com.whatsonnetflix.domain.repository.NetflixContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionSelectionViewModel @Inject constructor(
    private val repository: NetflixContentRepository,
    private val prefConfig: PrefConfig
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(RegionSelectionUiState())
    val uiState: StateFlow<RegionSelectionUiState> = _uiState.asStateFlow()

    init {
        getRegions()
    }

    fun onRetry() {
        getRegions()
    }

    private fun getRegions() {
        _uiState.update { it.copy(isLoading = true, hasError = false) }
        viewModelScope.launch {
            val result = repository.getAllRegions()
            _uiState.update {
                when (result) {
                    is Result.Success -> {
                        it.copy(regions = result.data, isLoading = false)
                    }
                    is Result.Error -> {
                        it.copy(isLoading = false, hasError = true)
                    }
                }
            }
        }
    }

    fun saveRegion(region: RegionModel) {
        prefConfig.saveRegionCode(region)
    }

    data class RegionSelectionUiState(
        val isLoading: Boolean = false,
        val hasError: Boolean = false,
        val regions: List<RegionModel> = emptyList()
    )
}


