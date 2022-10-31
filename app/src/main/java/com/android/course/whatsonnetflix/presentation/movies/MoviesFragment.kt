package com.android.course.whatsonnetflix.presentation.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.course.whatsonnetflix.databinding.FragmentMoviesBinding
import com.android.course.whatsonnetflix.presentation.contentdetail.ContentDetailFragmentDirections
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewAdapter
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewListener
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
            }
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

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = moviesViewModel
        return binding.root
    }

}