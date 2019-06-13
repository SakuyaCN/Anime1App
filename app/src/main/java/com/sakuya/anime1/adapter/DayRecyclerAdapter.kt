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
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.sakuya.anime1.entity.bean.newAnime
import com.sakuya.anime1.ui.view.coloredshadow.ShadowImageView

class DayRecyclerAdapter (): RecyclerView.Adapter<DayRecyclerAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = 7

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.tag = position
        holder.title.text = getPostionDay(position)
        Glide.with(holder.shadowImg.context)
            .load(getBackgroundColor(position))
            .placeholder(R.drawable.jb_red)
            .error(R.drawable.jb_red)
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

    @DrawableRes
    fun getBackgroundColor(position: Int):Int{
        when(position){
            0-> return R.drawable.jb_red
            1-> return R.drawable.jb_org
            2-> return R.drawable.jb_yel
            3-> return R.drawable.jb_gr
            4-> return R.drawable.jb_bl
            5-> return R.drawable.jb_d
            6-> return R.drawable.jb_z
            else -> return R.drawable.jb_red
        }
    }
    fun getPostionDay(position: Int):String{
        when(position){
            0-> return "星期日"
            1-> return "星期一"
            2-> return "星期二"
            3-> return "星期三"
            4-> return "星期四"
            5-> return "星期五"
            6-> return "星期六"
            else -> return "？？？"
        }
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        var shadowImg:ShadowImageView = v.findViewById(R.id.shadow_imag)
        val title: TextView = v.findViewById(R.id.tv_title)

        init {
            val params = shadowImg.layoutParams as ConstraintLayout.LayoutParams
            val windowManager = shadowImg.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val defaultDisplay = windowManager.defaultDisplay
            val point = Point()
            defaultDisplay.getSize(point)
            val x = point.x
            params.width = x - x/6
            shadowImg.layoutParams = params
        }
    }
}