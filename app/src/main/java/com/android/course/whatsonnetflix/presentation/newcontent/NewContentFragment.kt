package com.android.course.whatsonnetflix.presentation.newcontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.android.course.whatsonnetflix.databinding.FragmentNewContentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NewContentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewContentBinding.inflate(layoutInflater)
        val newContentViewModel = ViewModelProvider(this)[NewContentViewModel::class.java]
        val adapter = NewContentAdapter(NewContentAdapter.NewContentListener { contentId ->
            //TODO: implement navigation to new content details
            Timber.i("Content was pressed!. Here's the id: $contentId")
        })

        binding.newContentList.adapter = adapter

        newContentViewModel.newContents.observe(viewLifecycleOwner) { newContents ->
            newContents?.let {
                adapter.submitList(it)
            }

        }

        binding.lifecycleOwner = this
        binding.viewModel = newContentViewModel
        return binding.root
    }



}