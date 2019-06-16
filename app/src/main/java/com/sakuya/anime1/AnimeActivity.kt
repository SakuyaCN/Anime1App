package com.sakuya.anime1

import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.TransitionInflater
import android.util.Log
import android.view.KeyEvent
import android.view.Window
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.hawk.Hawk
import com.sakuya.App
import com.sakuya.anime1.ui.LoadActivity
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
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
//        val exp = TransitionInflater.from(this).inflateTransition(android.R.transition.explode)
////        window.exitTransition = exp
////        window.enterTransition = exp
////        window.reenterTransition = exp
//        val explode = Fade()
//        explode.duration = 500
//        window.exitTransition = explode
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
    private var firstTime = 0L

   override fun onKeyDown(keyCode:Int, event: KeyEvent):Boolean
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show()
                firstTime = System.currentTimeMillis()
            } else {
                finish();
                System.exit(0)
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}