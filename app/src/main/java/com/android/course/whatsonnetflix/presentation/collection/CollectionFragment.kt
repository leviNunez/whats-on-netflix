package com.android.course.whatsonnetflix.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.presentation.collection.CollectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class CollectionFragment : Fragment() {
    private lateinit var collectionPagerAdapter: CollectionPagerAdapter
    private lateinit var viewPager: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectionPagerAdapter = CollectionPagerAdapter(this)
        viewPager = view.findViewById<ViewPager2>(R.id.pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        val newContentTabTitle = getString(R.string.new_content_tab)
        val deletedContentTabTitle = getString(R.string.deleted_content_tab)
        val tabsTitle = listOf(newContentTabTitle, deletedContentTabTitle)

        viewPager.adapter = collectionPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsTitle[position]
        }.attach()
    }

}



