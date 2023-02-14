package com.whatsonnetflix.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.whatsonnetflix.R
import com.whatsonnetflix.ui.adapter.CategoryAdapter
import com.whatsonnetflix.ui.adapter.NetflixItemClickListener
import com.whatsonnetflix.databinding.FragmentHomeBinding
import com.whatsonnetflix.domain.NetflixItemModel
import com.whatsonnetflix.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = findNavController()
        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(WelcomeScreenFragment.REGION_SELECTED)
            .observe(currentBackStackEntry) { regionSelected ->
                if (regionSelected) {
                    viewModel.loadRegionAndRefreshCategories()
                } else {
                    requireActivity().finishAffinity()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.viewModel = viewModel
        binding.clickListener = RetryButtonClickListener { viewModel.onRetry() }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        bindState()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.action_bar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun bindState() {
        val categoryAdapter = CategoryAdapter(itemClickListener = NetflixItemClickListener {
            navigateToContentDetail(it)
        }, titles = resources.getStringArray(R.array.titles))
        categoryAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.categoriesListRv.adapter = categoryAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        if (uiState.hasNoRegion) navigateToRegionSelection()
                    }
                }
                launch {
                    viewModel.getCategories().collect { categories ->
                        viewModel.onListCollected(categories)
                        categoryAdapter.submitList(categories)
                    }
                }
            }
        }
    }

    private fun navigateToContentDetail(netflixItemModel: NetflixItemModel) {
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToContentDetailFragment2(netflixItemModel)
        )
    }

    private fun navigateToRegionSelection() {
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToRegionSelectionFragment()
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkRegion()
    }

}

