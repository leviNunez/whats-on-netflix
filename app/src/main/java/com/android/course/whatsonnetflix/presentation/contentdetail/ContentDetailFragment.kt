package com.android.course.whatsonnetflix.presentation.contentdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.databinding.FragmentContentDetailBinding
import com.android.course.whatsonnetflix.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ContentDetailFragment : Fragment() {

    private val contentDetailViewModel: ContentDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val actionBar = requireActivity().actionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        val binding = FragmentContentDetailBinding.inflate(layoutInflater)

        contentDetailViewModel.status.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (it) {
                    ContentApiStatus.LOADING -> {
                        binding.circularProgressIndicatorContainer.visibility = View.VISIBLE
                        binding.scrollview.visibility = View.GONE
                    }
                    else -> {
                        binding.circularProgressIndicatorContainer.visibility = View.GONE
                        binding.scrollview.visibility = View.VISIBLE
                    }
                }
            }
        }

        contentDetailViewModel.showToastEvent.observe(viewLifecycleOwner) { shouldShowToast ->
            if (shouldShowToast) {
                showToast(requireContext(), R.string.network_error_content_detail)
                contentDetailViewModel.doneShowingToast()
            }
        }

        binding.viewModel = contentDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }



}