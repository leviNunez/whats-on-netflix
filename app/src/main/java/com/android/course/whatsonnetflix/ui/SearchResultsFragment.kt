package com.android.course.whatsonnetflix.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.android.course.whatsonnetflix.databinding.FragmentSearchResultsBinding
import com.android.course.whatsonnetflix.ui.adapter.NetflixItemAdapter
import com.android.course.whatsonnetflix.ui.adapter.NetflixItemClickListener
import com.android.course.whatsonnetflix.ui.adapter.NetflixItemGridAdapter
import com.android.course.whatsonnetflix.viewmodel.SearchResultsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchResultsFragment : Fragment() {

    private val viewModel: SearchResultsViewModel by viewModels()
    private lateinit var binding: FragmentSearchResultsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultsBinding.inflate(layoutInflater)

        binding.viewModel = viewModel
        binding.clickListener = RetryButtonClickListener { viewModel.onRetry() }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val netflixItemAdapter = NetflixItemGridAdapter(onClickListener = NetflixItemClickListener {
            findNavController().navigate(
                SearchResultsFragmentDirections.actionSearchResultsFragmentToContentDetailFragment(
                    it
                )
            )
        })
        binding.searchResultsRv.adapter = netflixItemAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    it.results?.let { searchResults ->
                        netflixItemAdapter.submitList(searchResults)
                    }
                }
            }
        }
    }

}