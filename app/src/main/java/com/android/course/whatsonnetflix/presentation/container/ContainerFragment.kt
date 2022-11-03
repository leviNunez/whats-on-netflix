package com.android.course.whatsonnetflix.presentation.container

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.databinding.FragmentContainerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.HttpException

@AndroidEntryPoint
class ContainerFragment : Fragment() {
    private lateinit var containerPagerAdapter: HomePagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentContainerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContainerBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        containerPagerAdapter = HomePagerAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = containerPagerAdapter

        val tabLayout = binding.tabLayout
        val tabsTitles = listOf(getString(R.string.series_tab), getString(R.string.movies_tab))
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()

    }

    private fun setupMenu() {
        binding.containerToolbar.inflateMenu(R.menu.action_bar_menu)
        binding.containerToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
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
    }

}



