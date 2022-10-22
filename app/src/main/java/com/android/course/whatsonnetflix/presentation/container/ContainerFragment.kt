package com.android.course.whatsonnetflix.presentation.container

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.databinding.FragmentContainerBinding
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
                    binding.linearProgressIndicator.visibility = View.VISIBLE
                } else {
                    binding.linearProgressIndicator.visibility = View.GONE
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


}



