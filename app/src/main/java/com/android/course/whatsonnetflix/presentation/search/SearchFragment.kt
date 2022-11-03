package com.android.course.whatsonnetflix.presentation.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.databinding.FragmentSearchBinding
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewAdapter
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewListener
import com.android.course.whatsonnetflix.presentation.contentdetail.ContentDetailFragmentDirections
import com.android.course.whatsonnetflix.utils.scrollToTopOfList
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(layoutInflater)

        val netflixContentPreviewAdapter =
            NetflixContentPreviewAdapter(NetflixContentPreviewListener {
                searchViewModel.displayNetflixContentPreviewDetails(it)
            })

        val searchHistoryAdapterAdapter =
            SearchHistoryAdapter(NetflixSearchHistoryItemListener({
                searchViewModel.displaySearchHistoryItemDetails(it)
            }, {
                searchViewModel.deleteSearchHistoryItemFromDatabase(it)
            }))

        binding.searchResultsList.adapter = netflixContentPreviewAdapter
        binding.searchHistoryList.adapter = searchHistoryAdapterAdapter

        searchViewModel.status.observe(viewLifecycleOwner) { status ->
            status?.let {
                val query = searchViewModel.searchQuery.value
                when (it) {
                    ContentApiStatus.LOADING -> {
                        binding.flSearchLists.visibility = View.GONE
                        binding.searchQueryText.visibility = View.VISIBLE
                        binding.searchQueryText.text =
                            getString(R.string.searching_for_query, query)
                    }
                    ContentApiStatus.DONE -> {
                        if (searchViewModel.isSearchResultsEmpty) {
                            binding.flSearchLists.visibility = View.GONE
                            binding.searchQueryText.visibility = View.VISIBLE
                            binding.searchQueryText.text =
                                getString(R.string.search_no_results_found, query)
                        } else {
                            binding.flSearchLists.visibility = View.VISIBLE
                            binding.searchQueryText.visibility = View.GONE
                        }
                    }
                    ContentApiStatus.ERROR -> {
                        binding.flSearchLists.visibility = View.GONE
                        binding.searchQueryText.visibility = View.VISIBLE
                    }
                }
            }

        }

        searchViewModel.searchResults.observe(viewLifecycleOwner) { searchResults ->
            searchResults?.let {
                netflixContentPreviewAdapter.submitList(it)
                binding.searchResultsList.scrollToTopOfList(viewLifecycleOwner.lifecycleScope)
            }
        }

        searchViewModel.searchHistory.observe(viewLifecycleOwner) { searchHistory ->
            searchHistory?.let {
                searchHistoryAdapterAdapter.submitList(it)
            }
        }

        searchViewModel.navigateToSelectedContent.observe(viewLifecycleOwner) { netflixSearchHistoryItem ->
            netflixSearchHistoryItem?.let {
                val navController = findNavController()
                navController.navigate(
                    ContentDetailFragmentDirections.actionGlobalContentDetailFragment(
                        it
                    )
                )
                searchViewModel.doneNavigating()
            }
        }

        binding.viewModel = searchViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_24)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupQueryTextListener()
    }

    private fun setupQueryTextListener() {
        val searchView: SearchView = binding.searchView
        searchView.isIconified = false
        searchView.setIconifiedByDefault(false)

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.handleSearch(it)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    searchViewModel.handleSearch(it)
                }
                return true
            }
        })
    }
}
