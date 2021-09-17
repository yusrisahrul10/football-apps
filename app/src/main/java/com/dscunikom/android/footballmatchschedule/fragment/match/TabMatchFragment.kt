package com.dscunikom.android.footballmatchschedule.fragment.match

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dscunikom.android.footballmatchschedule.R

class TabMatchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val x = inflater.inflate(R.layout.tab_layout, container, false)

        tabLayout = x.findViewById<View>(
            R.id.tabs
        ) as TabLayout
        viewPager = x.findViewById<View>(
            R.id.viewpager
        ) as ViewPager

        viewPager.adapter = MyAdapter(childFragmentManager)
        tabLayout.post {
            tabLayout.setupWithViewPager(
                viewPager
            )
        }
        return x
    }

    internal inner class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return int_items
        }

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return PastMatchFragment()
                1 -> return NextMatchFragment()
            }
            return null
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Last Match"
                1 -> return "Next Match"
            }
            return null
        }

    }

    companion object {
        lateinit var tabLayout: TabLayout
        lateinit var viewPager: ViewPager
        var int_items = 2
    }


}