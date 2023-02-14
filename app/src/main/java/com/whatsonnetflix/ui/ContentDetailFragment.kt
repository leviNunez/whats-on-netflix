package com.whatsonnetflix.ui

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
import com.whatsonnetflix.ui.adapter.NetflixItemClickListener
import com.whatsonnetflix.databinding.FragmentContentDetailBinding
import com.whatsonnetflix.ui.adapter.NetflixItemGridAdapter
import com.whatsonnetflix.viewmodel.ContentDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentDetailFragment : Fragment() {

    private val viewModel: ContentDetailViewModel by viewModels()
    lateinit var binding: FragmentContentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentDetailBinding.inflate(layoutInflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val netflixItemAdapter = NetflixItemGridAdapter(onClickListener = NetflixItemClickListener {
            findNavController().navigate(
                ContentDetailFragmentDirections.actionContentDetailFragmentSelf(
                    it
                )
            )
        })
        binding.moreLikeThisRv.adapter = netflixItemAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moreLikeThis.collect { netflixItemList ->
                    netflixItemList?.let {
                        netflixItemAdapter.submitList(it)
                    }
                }
            }
        }
    }


}