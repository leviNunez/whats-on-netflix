package com.android.course.whatsonnetflix.presentation.tvshows

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.databinding.FragmentTvShowsBinding
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewAdapter
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewListener
import com.android.course.whatsonnetflix.presentation.contentdetail.ContentDetailFragmentDirections
import com.android.course.whatsonnetflix.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TvShowsFragment : Fragment() {

    private val tvShowsViewModel: TvShowsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTvShowsBinding.inflate(layoutInflater)

        val adapter = NetflixContentPreviewAdapter(NetflixContentPreviewListener {
            tvShowsViewModel.displayContentDetails(it.netflixId)
        })

        binding.tvShowsList.adapter = adapter

        tvShowsViewModel.tvShows.observe(viewLifecycleOwner) { tvShows ->
            tvShows?.let {
                adapter.submitList(it)
            }
        }

        binding.tvShowsSwipeRefresh.setOnRefreshListener {
            tvShowsViewModel.refreshContent()
            binding.tvShowsSwipeRefresh.isRefreshing = false
        }

        tvShowsViewModel.navigateToSelectedContent.observe(viewLifecycleOwner) { contentId ->
            contentId?.let {
                val navController = findNavController()
                navController.navigate(
                    ContentDetailFragmentDirections.actionGlobalContentDetailFragment(
                        it
                    )
                )
                tvShowsViewModel.doneNavigating()
            }
        }

        tvShowsViewModel.showToastEvent.observe(viewLifecycleOwner) { shouldShowToast ->
            if (shouldShowToast) {
                showToast(requireContext(), R.string.network_error_feed)
                tvShowsViewModel.doneShowingToast()
            }
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = tvShowsViewModel
        return binding.root
    }


}