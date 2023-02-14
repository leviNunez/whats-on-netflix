package com.whatsonnetflix.ui

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
import com.whatsonnetflix.R
import com.whatsonnetflix.ui.adapter.RegionModelAdapter
import com.whatsonnetflix.ui.adapter.RegionItemClickListener
import com.whatsonnetflix.databinding.FragmentRegionUpdateBinding
import com.whatsonnetflix.domain.RegionModel
import com.whatsonnetflix.viewmodel.RegionSelectionViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegionUpdateFragment : Fragment() {

    companion object {
        const val REGION_UPDATED: String = "REGION_UPDATED"
    }

    private val viewModel: RegionSelectionViewModel by activityViewModels()
    private lateinit var binding: FragmentRegionUpdateBinding
    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegionUpdateBinding.inflate(layoutInflater, container, false)

        binding.viewModel = viewModel
        binding.clickListener = RetryButtonClickListener { viewModel.onRetry() }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle[REGION_UPDATED] = false

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    val regionModelAdapter =
                        RegionModelAdapter(onClickListener = RegionItemClickListener { selectedRegion ->
                            viewModel.saveRegion(selectedRegion)
                            savedStateHandle[REGION_UPDATED] = true
                            showSnackbar(selectedRegion)
                            findNavController().popBackStack()
                        })
                    binding.regionListContainer.regionListRv.adapter = regionModelAdapter
                    regionModelAdapter.submitList(uiState.regions)
                }
            }
        }
    }

    private fun showSnackbar(region: RegionModel) {
        Snackbar.make(
            requireView(),
            getString(R.string.region_updated, region.country),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}