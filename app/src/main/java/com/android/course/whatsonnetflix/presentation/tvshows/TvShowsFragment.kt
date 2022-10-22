package com.android.course.whatsonnetflix.presentation.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.course.whatsonnetflix.databinding.FragmentTvShowsBinding
import com.android.course.whatsonnetflix.presentation.contentdetail.ContentDetailFragmentDirections
import com.android.course.whatsonnetflix.utils.ContentPreviewListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsFragment : Fragment() {

    private val tvShowsViewModel: TvShowsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTvShowsBinding.inflate(layoutInflater)

        val adapter = TvShowsAdapter(ContentPreviewListener { contentId ->
            tvShowsViewModel.displayContentDetails(contentId)
        })

        binding.newContentList.adapter = adapter

        tvShowsViewModel.tvShows.observe(viewLifecycleOwner) { tvShows ->
            tvShows?.let {
                adapter.submitList(it)
            }
        }

        tvShowsViewModel.navigateToSelectedContent.observe(viewLifecycleOwner) { contentId ->
            contentId?.let {
                val navController = findNavController()
                navController.navigate(
                    ContentDetailFragmentDirections.actionGlobalContentDetailFragment2(
                        it
                    )
                )
                tvShowsViewModel.doneNavigating()
            }
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = tvShowsViewModel
        return binding.root
    }


}