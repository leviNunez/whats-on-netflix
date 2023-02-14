package com.whatsonnetflix.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.whatsonnetflix.R
import com.whatsonnetflix.databinding.FragmentSearchableBinding
import com.whatsonnetflix.ui.adapter.SearchHistoryAdapter
import com.whatsonnetflix.ui.adapter.SearchHistoryItemClickListener
import com.whatsonnetflix.viewmodel.SearchableViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchableFragment : Fragment() {
    private val viewModel: SearchableViewModel by viewModels()
    private lateinit var binding: FragmentSearchableBinding
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchableBinding.inflate(layoutInflater)
        searchView = requireActivity().findViewById(R.id.search_view)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchHistoryAdapter =
            SearchHistoryAdapter(onClickListener = SearchHistoryItemClickListener { item, clickedView ->
                when (clickedView.id) {
                    R.id.delete_history_icon_iv -> viewModel.deleteItem(item)
                    else -> searchView.setQuery(item.searchTerm, true)
                }
            })

        bindSearchHistoryList(searchHistoryAdapter)
        setupSearch(searchHistoryAdapter)
    }

    private fun bindSearchHistoryList(searchHistoryAdapter: SearchHistoryAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSearchHistory().collect { searchHistory ->
                    binding.searchHistoryRv.adapter = searchHistoryAdapter
                    searchHistoryAdapter.cacheDataAndSubmitList(searchHistory)
                }
            }
        }
    }

    private fun setupSearch(adapter: SearchHistoryAdapter) {
        searchView.apply {
            isIconified = false
            requestFocus()
            setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(newText: String?): Boolean {
                    if (!newText.isNullOrEmpty()) {
                        viewModel.addQueryToSearchHistory(newText)
                        findNavController().navigate(
                            SearchableFragmentDirections.actionSearchableFragmentToSearchResultsFragment(
                                newText
                            )
                        )
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { text ->
                        adapter.filter.filter(text)
                    }
                    return true
                }
            })
        }
    }
}
