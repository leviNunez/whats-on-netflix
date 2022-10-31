package com.android.course.whatsonnetflix.presentation.container

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.databinding.FragmentContainerBinding
import com.android.course.whatsonnetflix.presentation.contentdetail.ContentDetailFragmentDirections
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContainerFragment : Fragment() {
    private lateinit var containerPagerAdapter: HomePagerAdapter
    private lateinit var viewPager: ViewPager2
    private val containerViewModel: ContainerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentContainerBinding.inflate(layoutInflater)

        containerViewModel.status.observe(viewLifecycleOwner) {
            it?.let {
                if (it == ContentApiStatus.LOADING) {
                    binding.containerCircularProgressIndicator.visibility = View.VISIBLE
                } else {
                    binding.containerCircularProgressIndicator.visibility = View.GONE
                }
            }
        }

        containerViewModel.showToastEvent.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_LONG).show()
                containerViewModel.doneShowingToast()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()

        containerPagerAdapter = HomePagerAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = containerPagerAdapter

        val tabsTitle = listOf(
            getString(R.string.tv_shows_tab),
            getString(R.string.movies_tab)
        )
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsTitle[position]
        }.attach()

    }

    private fun setupMenu() {
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.action_bar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        val navController = findNavController()
                        navController.navigate(
                            ContainerFragmentDirections.actionContainerFragmentToSearchFragment()
                        )
                        true
                    }
                    else -> false

                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}



