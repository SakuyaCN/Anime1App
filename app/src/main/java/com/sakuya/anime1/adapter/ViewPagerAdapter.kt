package com.sakuya.anime1.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(var fragments:List<Fragment>,var fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        fm.beginTransaction().show(fragment).commitAllowingStateLoss()
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = fragments.get(position)
        fm.beginTransaction().hide(fragment).commitAllowingStateLoss()
    }

}