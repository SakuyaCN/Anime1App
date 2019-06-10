package com.sakuya.anime1

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import me.yokeyword.fragmentation.SupportActivity
import com.sakuya.anime1.ui.fragment.MainFragment
import kotlinx.android.synthetic.main.fragment_main.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class AnimeActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime)
        init()
    }

    private fun init(){
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        if (findFragment(MainFragment::class.java) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance())
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}