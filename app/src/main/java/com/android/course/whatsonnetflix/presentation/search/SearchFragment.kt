package com.android.course.whatsonnetflix.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.databinding.FragmentSearchBinding
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewAdapter
import com.android.course.whatsonnetflix.presentation.NetflixContentPreviewListener
import com.android.course.whatsonnetflix.presentation.contentdetail.ContentDetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("Fragment view is being created")
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
                when (it) {
                    ContentApiStatus.LOADING -> {
                        binding.searchProgressIndicator.visibility = View.VISIBLE
                        binding.clListsSpace.visibility = View.GONE
                    }
                    else -> {
                        binding.searchProgressIndicator.visibility = View.GONE
                        binding.clListsSpace.visibility = View.VISIBLE
                        scrollToTop()
                    }
                }
            }

        }

        searchViewModel.searchResults.observe(viewLifecycleOwner) { searchResults ->
            searchResults?.let {
                netflixContentPreviewAdapter.submitList(it)
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

    override fun onResume() {
        super.onResume()
        val searchView: androidx.appcompat.widget.SearchView = binding.searchView
        searchView.requestFocus()
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
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

    private fun scrollToTop() {
        lifecycleScope.launch {
            delay(100)
            binding.searchResultsList.scrollToPosition(0)
        }
    }

}
