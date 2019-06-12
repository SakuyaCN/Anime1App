package com.sakuya.anime1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.hawk.Hawk
import com.sakuya.App
import me.yokeyword.fragmentation.SupportActivity
import com.sakuya.anime1.ui.fragment.MainFragment
import kotlinx.android.synthetic.main.fragment_main.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.lang.ref.WeakReference

class AnimeActivity : SupportActivity() {

    companion object {
        var weak: WeakReference<AnimeActivity>? = null

        fun finishActivity() {
            if (weak != null && weak!!.get() != null) {
                weak!!.get()!!.finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime)
        weak = WeakReference(this)
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