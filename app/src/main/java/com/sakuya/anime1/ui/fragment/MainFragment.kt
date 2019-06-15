package com.sakuya.anime1.ui.fragment

import me.yokeyword.fragmentation.SupportFragment
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.sakuya.anime1.R
import com.sakuya.anime1.adapter.ViewPagerAdapter
import com.sakuya.anime1.ui.fragment.anime.AnimeFragment
import com.sakuya.anime1.ui.fragment.anli.AnLiFragment
import com.sakuya.anime1.ui.fragment.setting.SettingFragment
import android.animation.LayoutTransition

class MainFragment : SupportFragment() {

    private val SELECT_WEIGHT = 2f
    private val SELECT_SIZE = 20f
    private val UNSELECT_SIZE = 16f
    lateinit var tab_layout :TabLayout
    lateinit var colllayout:CollapsingToolbarLayout
    lateinit var app_bar:AppBarLayout
    lateinit var view_Pager :ViewPager
    private val mFragments = arrayListOf<Fragment>()
    private val titles = arrayOf("最新更新","安利墙","设置")

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
        val lt = LayoutTransition()
        lt.disableTransitionType(LayoutTransition.CHANGE_APPEARING)
        tab_layout.setLayoutTransition(lt)
        view_Pager = view.findViewById(R.id.view_pager)
        app_bar = view.findViewById(R.id.app_bar)
        colllayout = view.findViewById(R.id.colllayout)
        mFragments.add(AnimeFragment.newInstance())
        mFragments.add(AnLiFragment.newInstance())
        mFragments.add(SettingFragment.newInstance())
        view_Pager.adapter = ViewPagerAdapter(mFragments,fragmentManager!!)
        tab_layout.setupWithViewPager(view_Pager)
        tab_layout.post {
            for (index in 0 until mFragments.size) {
                val tabAt = tab_layout.getTabAt(index)
                tabAt!!.setCustomView(R.layout.tab_item)
                var textview = tabAt.customView!!.findViewById<TextView>(R.id.tv_tab)
                var view = (tab_layout.getChildAt(0) as LinearLayout).getChildAt(index)
                if (index == 0) {
                    val tabParams = view.layoutParams as LinearLayout.LayoutParams
                    tabParams.weight = SELECT_WEIGHT
                    view.layoutParams = tabParams
                    textview!!.textSize = SELECT_SIZE
                }
                else {
                    textview.textSize = UNSELECT_SIZE
                }

                tabAt.customView!!.findViewById<TextView>(R.id.tv_tab).text = titles[index]
            }
        }
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                changeTabStyle(tab!!,false)

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                changeTabStyle(tab!!,true)
                if(tab!!.position==1){
                    app_bar.setExpanded(false)
                }else{
                    app_bar.setExpanded(true)
                }
            }

        })
    }

    fun changeTabStyle(tab: TabLayout.Tab,isSelect:Boolean){
        tab_layout.apply {
            var textview = tab!!.customView!!.findViewById<TextView>(R.id.tv_tab)
            textview.textSize = if(isSelect) SELECT_SIZE else UNSELECT_SIZE

            var view = (this.getChildAt(0) as LinearLayout).getChildAt(tab.position)
            val tabParams = view.layoutParams as LinearLayout.LayoutParams
            tabParams.weight = if(isSelect) SELECT_WEIGHT else 1f
            view.layoutParams = tabParams
        }

    }
}