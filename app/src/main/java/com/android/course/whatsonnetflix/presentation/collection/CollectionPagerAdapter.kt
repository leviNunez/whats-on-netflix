package com.android.course.whatsonnetflix.presentation.collection

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.course.whatsonnetflix.presentation.deletedcontent.DeletedContentFragment
import com.android.course.whatsonnetflix.presentation.newcontent.NewContentFragment

private const val NUM_TABS = 3

class CollectionPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        when (position) {
            0 -> return NewContentFragment()
        }
        return DeletedContentFragment()
    }
}