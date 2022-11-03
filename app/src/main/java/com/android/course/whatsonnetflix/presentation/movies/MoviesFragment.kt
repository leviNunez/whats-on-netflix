package com.android.course.whatsonnetflix.presentation.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.databinding.FragmentMoviesBinding
import com.android.course.whatsonnetflix.presentation.contentdetail.ContentDetailFragmentDirections
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewAdapter
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewListener
import com.android.course.whatsonnetflix.utils.scrollToTopOfList
import com.android.course.whatsonnetflix.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMoviesBinding.inflate(layoutInflater)

        val adapter = NetflixContentPreviewAdapter(NetflixContentPreviewListener {
            moviesViewModel.displayContentDetails(it.netflixId)
        })

        binding.moviesList.adapter = adapter

        moviesViewModel.movies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                adapter.submitList(it)
                binding.moviesList.scrollToTopOfList(viewLifecycleOwner.lifecycleScope)
            }
        }

        binding.moviesSwipeRefresh.setOnRefreshListener {
            moviesViewModel.refreshContent()
            binding.moviesSwipeRefresh.isRefreshing = false
        }

        moviesViewModel.navigateToSelectedContent.observe(viewLifecycleOwner) { contentId ->
            contentId?.let {
                val navController = findNavController()
                navController.navigate(
                    ContentDetailFragmentDirections.actionGlobalContentDetailFragment(
                        it
                    )
                )
                moviesViewModel.doneNavigating()
            }
        }

        moviesViewModel.showToastEvent.observe(viewLifecycleOwner) { shouldShowToast ->
            if (shouldShowToast) {
                showToast(requireContext(), R.string.network_error_feed)
                moviesViewModel.doneShowingToast()
            }
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = moviesViewModel
        return binding.root
    }

}