package com.sakuya.anime1.ui.fragment.anli

import me.yokeyword.fragmentation.SupportFragment
import android.os.Bundle

import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.viewpager.widget.ViewPager
import com.sakuya.anime1.R
import com.sakuya.anime1.entity.CardItem
import com.sakuya.anime1.ui.view.cardpager.CardPagerAdapter
import com.sakuya.anime1.ui.view.cardpager.ShadowTransformer


class AnLiFragment : SupportFragment() {

    private var mCardShadowTransformer: ShadowTransformer? = null
    private var mCardAdapter: CardPagerAdapter? = null
    private lateinit var viewPager: ViewPager
    companion object{
        fun newInstance(): AnLiFragment {
            val args = Bundle()

            val fragment = AnLiFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_anli, container, false)
        initView(view)
        initData()
        return view
    }

    private fun initView(view:View) {
        viewPager = view.findViewById(R.id.viewPager)
    }

    private fun initData(){
        mCardAdapter = CardPagerAdapter()
        mCardAdapter!!.addCardItem(CardItem("",""))
        mCardAdapter!!.addCardItem(CardItem("",""))
        mCardAdapter!!.addCardItem(CardItem("",""))
        mCardAdapter!!.addCardItem(CardItem("",""))
        mCardAdapter!!.addCardItem(CardItem("",""))
        mCardShadowTransformer = ShadowTransformer(viewPager, mCardAdapter!!)
        viewPager.adapter = mCardAdapter!!
        viewPager.setPageTransformer(false,mCardShadowTransformer)
        viewPager.offscreenPageLimit = 3
    }
}