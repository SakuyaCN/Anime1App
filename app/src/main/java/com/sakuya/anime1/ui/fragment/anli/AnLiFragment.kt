package com.sakuya.anime1.ui.fragment.anli

import android.graphics.Bitmap
import android.graphics.Color
import me.yokeyword.fragmentation.SupportFragment
import android.os.Bundle
import android.util.Log

import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.viewpager.widget.ViewPager
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.load.MultiTransformation
import com.sakuya.anime1.R
import com.sakuya.anime1.adapter.AnimeRecyclerAdapter
import com.sakuya.anime1.entity.CardItem
import com.sakuya.anime1.entity.bean.newAnime
import com.sakuya.anime1.entity.bean.songShare
import com.sakuya.anime1.ui.view.cardpager.CardPagerAdapter
import com.sakuya.anime1.ui.view.cardpager.ShadowTransformer
import jp.wasabeef.glide.transformations.BlurTransformation


class AnLiFragment : SupportFragment() {

    private var mCardShadowTransformer: ShadowTransformer? = null
    private var mCardAdapter: CardPagerAdapter? = null
    private lateinit var viewPager: ViewPager
    private lateinit var btn_more : LottieAnimationView
    private lateinit var btn_srarch : LottieAnimationView
    companion object {
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
        initBmob()
        initClick()
        return view
    }

    private fun initClick() {
        btn_more.setOnClickListener {
            btn_more.playAnimation()
        }
        btn_srarch.setOnClickListener {
            btn_srarch.playAnimation()
        }
    }

    private fun initView(view: View) {
        viewPager = view.findViewById(R.id.viewPager)
        btn_more = view.findViewById(R.id.more_view)
        btn_srarch = view.findViewById(R.id.search_view)

    }

    private fun initData() {
        mCardAdapter = CardPagerAdapter()
        mCardShadowTransformer = ShadowTransformer(viewPager, mCardAdapter!!)
        viewPager.setPageTransformer(false, mCardShadowTransformer)
    }

    private fun initBmob() {
        queryObjects()
    }

    private fun queryObjects() {
        var bmobQuery: BmobQuery<songShare> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<songShare>() {
            override fun done(list: MutableList<songShare>?, ex: BmobException?) {
                if (ex == null) {
                    if(list!!.isNotEmpty()){
                        list.forEach {
                            mCardAdapter!!.addCardItem(it)
                            viewPager.adapter = mCardAdapter!!
                            viewPager.offscreenPageLimit = 3
                            mCardShadowTransformer!!.enableScaling(true)
                        }
                    }
                } else {
                    Log.e("error", ex.message)
                }
            }
        })
    }
}