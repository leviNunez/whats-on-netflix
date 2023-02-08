package com.android.course.whatsonnetflix.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.android.course.whatsonnetflix.ui.adapter.RegionModelAdapter
import com.android.course.whatsonnetflix.ui.adapter.RegionItemClickListener
import com.android.course.whatsonnetflix.databinding.FragmentWelcomeScreenBinding
import com.android.course.whatsonnetflix.domain.RegionModel
import com.android.course.whatsonnetflix.viewmodel.RegionSelectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeScreenFragment : Fragment() {

    companion object {
        const val REGION_SELECTED: String = "REGION_SELECTED"
    }

    private val regionSelectionViewModel: RegionSelectionViewModel by activityViewModels()
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var binding: FragmentWelcomeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle[REGION_SELECTED] = false

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                regionSelectionViewModel.uiState.collect { uiState ->
                    if (uiState.isLoading) {
                        showProgressIndicator()
                    } else {
                        showRegionsList()
                        bindRegionsList(uiState.regions)
                    }
                }
            }
        }
    }

    private fun showProgressIndicator() {
        binding.regionListContainer.progressIndicator.visibility = View.VISIBLE
        binding.regionListContainer.regionListRv.visibility = View.GONE
    }

    private fun showRegionsList() {
        binding.regionListContainer.progressIndicator.visibility = View.GONE
        binding.regionListContainer.regionListRv.visibility = View.VISIBLE
    }

    private fun bindRegionsList(regions: List<RegionModel>) {
        val regionModelAdapter =
            RegionModelAdapter(onClickListener = RegionItemClickListener {
                regionSelectionViewModel.saveRegion(it)
                savedStateHandle[REGION_SELECTED] = true
                findNavController().popBackStack()
            })
        binding.regionListContainer.regionListRv.adapter = regionModelAdapter
        regionModelAdapter.submitList(regions)
    }
}