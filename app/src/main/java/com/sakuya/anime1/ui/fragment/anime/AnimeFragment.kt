package com.sakuya.anime1.ui.fragment.anime

import me.yokeyword.fragmentation.SupportFragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.sakuya.anime1.R
import com.sakuya.anime1.adapter.ViewPagerAdapter
import org.w3c.dom.Text

class AnimeFragment : SupportFragment() {

    companion object{
        fun newInstance(): AnimeFragment {
            val args = Bundle()

            val fragment = AnimeFragment()
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

    }
}