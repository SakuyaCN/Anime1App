package com.sakuya.anime1.ui.fragment

import me.yokeyword.fragmentation.SupportFragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.sakuya.anime1.R
import com.sakuya.anime1.adapter.ViewPagerAdapter
import com.sakuya.anime1.ui.fragment.anime.AnimeFragment
import com.sakuya.anime1.utils.SizeUtil
import org.w3c.dom.Text

class MainFragment : SupportFragment() {

    lateinit var tab_layout :TabLayout
    lateinit var view_Pager :ViewPager
    private val mFragments = arrayListOf<Fragment>()
    private val titles = arrayOf("Movies","TV","Web Serise")

    companion object{
        fun newInstance(): MainFragment {

            val args = Bundle()

            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        initView(view)
        return view
    }

    private fun initView(view:View) {
        tab_layout = view.findViewById(R.id.tab_layout)
        view_Pager = view.findViewById(R.id.view_pager)
        mFragments.add(AnimeFragment.newInstance())
        mFragments.add(AnimeFragment.newInstance())
        mFragments.add(AnimeFragment.newInstance())
        view_Pager.adapter = ViewPagerAdapter(mFragments,fragmentManager!!)
        tab_layout.setupWithViewPager(view_Pager)
        for (index in 0 until mFragments.size) {
            val tabAt = tab_layout.getTabAt(index)
            tabAt!!.setCustomView(R.layout.tab_item)
            if (index == 0) {
                tabAt.customView!!.findViewById<TextView>(R.id.tv_tab).textSize = 26f
            }
            tabAt.customView!!.findViewById<TextView>(R.id.tv_tab).text = titles[index]
        }
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab!!.customView!!.findViewById<TextView>(R.id.tv_tab).textSize = 18f
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab!!.customView!!.findViewById<TextView>(R.id.tv_tab).textSize = 26f
            }

        })
    }
}