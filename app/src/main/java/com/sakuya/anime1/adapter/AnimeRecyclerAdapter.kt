package com.sakuya.anime1.adapter

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sakuya.anime1.R
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.sakuya.anime1.entity.AnimeEntity
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sakuya.anime1.entity.bean.newAnime
import com.sakuya.anime1.ui.view.coloredshadow.ShadowImageView
import jp.wasabeef.glide.transformations.BlurTransformation

class AnimeRecyclerAdapter (var entitys: MutableList<newAnime>): RecyclerView.Adapter<AnimeRecyclerAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.anime_item, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = entitys.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.tag = position
        holder.time.text = "更新时间：${entitys[position].updateTime}"
        holder.title.text = entitys[position].animeName
        Glide.with(holder.shadowImg.context)
            .load(entitys[position].ImgUrl)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .placeholder(R.drawable.test)
            .error(R.drawable.test)
            .into(object : ViewTarget<ImageView, Drawable>(holder.shadowImg) {
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    holder.shadowImg.setImageDrawable(placeholder, withShadow = false)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    super.onLoadCleared(placeholder)
                    holder.shadowImg.setImageDrawable(placeholder, withShadow = false)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    holder.shadowImg.setImageDrawable(errorDrawable, withShadow = false)
                }

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    holder.shadowImg.setImageDrawable(resource)
                }
            })
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        var shadowImg:ShadowImageView = v.findViewById(R.id.shadow_imag)
        val title: TextView = v.findViewById(R.id.title)
        val time: TextView = v.findViewById(R.id.time)

        init {
            shadowImg.postDelayed({
                setMargin(shadowImg)
            },1)

        }

        fun setMargin(img:ImageView){
            val params = img.layoutParams as ConstraintLayout.LayoutParams
            val windowManager = img.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val defaultDisplay = windowManager.defaultDisplay
            val point = Point()
            defaultDisplay.getSize(point)
            val x = point.x
            params.width = x - x/6
            params.height = x
            img.layoutParams = params
            shadowImg.radiusOffset = 1f
        }
    }
}