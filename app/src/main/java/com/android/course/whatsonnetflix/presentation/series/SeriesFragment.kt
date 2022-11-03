package com.android.course.whatsonnetflix.presentation.series

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.databinding.FragmentSeriesBinding
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewAdapter
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewListener
import com.android.course.whatsonnetflix.presentation.contentdetail.ContentDetailFragmentDirections
import com.android.course.whatsonnetflix.utils.scrollToTopOfList
import com.android.course.whatsonnetflix.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesFragment : Fragment() {

    private val seriesViewModel: SeriesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSeriesBinding.inflate(layoutInflater)

        val adapter = NetflixContentPreviewAdapter(NetflixContentPreviewListener {
            seriesViewModel.displayContentDetails(it.netflixId)
        })

        binding.seriesList.adapter = adapter

        seriesViewModel.series.observe(viewLifecycleOwner) { tvShows ->
            tvShows?.let {
                adapter.submitList(it)
                binding.seriesList.scrollToTopOfList(viewLifecycleOwner.lifecycleScope)
            }
        }

        binding.seriesSwipeRefresh.setOnRefreshListener {
            seriesViewModel.refreshContent()
            binding.seriesSwipeRefresh.isRefreshing = false
        }

        seriesViewModel.navigateToSelectedContent.observe(viewLifecycleOwner) { contentId ->
            contentId?.let {
                val navController = findNavController()
                navController.navigate(
                    ContentDetailFragmentDirections.actionGlobalContentDetailFragment(
                        it
                    )
                )
                seriesViewModel.doneNavigating()
            }
        }

        seriesViewModel.showToastEvent.observe(viewLifecycleOwner) { shouldShowToast ->
            if (shouldShowToast) {
                showToast(requireContext(), R.string.network_error_feed)
                seriesViewModel.doneShowingToast()
            }
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = seriesViewModel
        return binding.root
    }


}