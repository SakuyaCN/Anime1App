package com.sakuya.anime1.ui.view.cardpager

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.sakuya.anime1.R
import com.sakuya.anime1.entity.bean.songShare
import com.sakuya.anime1.ui.view.coloredshadow.ShadowImageView

import java.util.ArrayList

class CardPagerAdapter : PagerAdapter(), CardAdapter {

    private var isPlaying = false
    private val mViews: MutableList<View?>
    private val mData: MutableList<songShare>

    init {
        mData = ArrayList()
        mViews = ArrayList()
    }

    fun addCardItem(item: songShare) {
        mViews.add(null)
        mData.add(item)
    }

    override fun getCardViewAt(position: Int): View {
        return mViews[position]!!
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.cardpager_item, container, false)
        container.addView(view)
        bind(mData[position], view)
        mViews[position] = view
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        mViews.set(position, null)
    }

    private var playViewState = false
    private fun bind(item: songShare, view: View) {
        var shadow_imag = view.findViewById<ShadowImageView>(R.id.shadow_imag)
        var play_view = view.findViewById<LottieAnimationView>(R.id.play_view)
        play_view.addAnimatorUpdateListener {
            if(isPlaying&&playViewState){
                if(play_view.progress > 0.5f)
                    play_view.pauseAnimation()
            }
        }
        play_view.setOnClickListener {
            if(!isPlaying) {
                isPlaying = true
                play_view.playAnimation()
                playViewState = true
            }else{
                isPlaying = false
                playViewState = false
                play_view.progress = 0.5f
                play_view.playAnimation()
            }
        }
        Glide.with(shadow_imag.context)
            .load(item.songImg)
            .into(object : ViewTarget<ImageView, Drawable>(shadow_imag) {
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    shadow_imag.setImageDrawable(placeholder, withShadow = false)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    super.onLoadCleared(placeholder)
                    shadow_imag.setImageDrawable(placeholder, withShadow = false)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    shadow_imag.setImageDrawable(errorDrawable, withShadow = false)
                }

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    shadow_imag.setImageDrawable(resource)
                }
            })
    }

    fun setMargin(img:ShadowImageView,view:View){
        val params = img.layoutParams as ConstraintLayout.LayoutParams
        params.width = view.measuredWidth
        params.height = view.measuredWidth
        img.layoutParams = params
    }

}
