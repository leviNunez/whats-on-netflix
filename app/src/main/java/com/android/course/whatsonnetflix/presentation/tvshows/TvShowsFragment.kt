package com.android.course.whatsonnetflix.presentation.tvshows

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.course.whatsonnetflix.databinding.FragmentTvShowsBinding
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewAdapter
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewListener
import com.android.course.whatsonnetflix.presentation.contentdetail.ContentDetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

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

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = tvShowsViewModel
        return binding.root
    }

}