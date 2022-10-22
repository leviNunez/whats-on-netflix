package com.android.course.whatsonnetflix.presentation.container

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.course.whatsonnetflix.presentation.movies.MoviesFragment
import com.android.course.whatsonnetflix.presentation.tvshows.TvShowsFragment

private const val NUM_TABS = 2

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUM_TABS

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            TvShowsFragment()
        } else {
            MoviesFragment()
        }
    }
}