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
import com.google.android.flexbox.FlexboxLayout
import com.sakuya.anime1.ui.view.coloredshadow.ShadowImageView
import androidx.core.view.ViewCompat
import android.view.Gravity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.media.Image
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.orhanobut.hawk.Hawk
import com.sakuya.anime1.ui.fragment.anime.AnimeFragment
import com.sakuya.anime1.utils.SizeUtil

class DayRecyclerAdapter (var array:Array<Array<String>>): RecyclerView.Adapter<DayRecyclerAdapter.VH>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = 7

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.tag = position
        holder.title.text = getPostionDay(position)
        array[position].forEach {
            holder.flex_box.addView(createNewFlexItemTextView(holder.flex_box.context,it))
        }
        if(Hawk.get("isColorful"))
            holder.shadowImg.setImageDrawable(GradientDrawable(GradientDrawable.Orientation.TR_BL, AnimeFragment.colors[AnimeFragment.randNum]).apply { this.cornerRadius = 20f })
        else
            Glide.with(holder.shadowImg.context)
                .load(ColorDrawable(ContextCompat.getColor(holder.shadowImg.context, R.color.colorPrimary_2)))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .into(holder.shadowImg.currentView as ImageView)
//        Glide.with(holder.shadowImg.context)
//            .load(getBackgroundColor(position))
//            .placeholder(R.drawable.jb_red)
//            .error(R.drawable.jb_red)
//            .into(object : ViewTarget<ImageView, Drawable>(holder.shadowImg) {
//                override fun onLoadStarted(placeholder: Drawable?) {
//                    super.onLoadStarted(placeholder)
//                    holder.shadowImg.setImageDrawable(placeholder, withShadow = false)
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    super.onLoadCleared(placeholder)
//                    holder.shadowImg.setImageDrawable(placeholder, withShadow = false)
//                }
//
//                override fun onLoadFailed(errorDrawable: Drawable?) {
//                    super.onLoadFailed(errorDrawable)
//                    holder.shadowImg.setImageDrawable(errorDrawable, withShadow = false)
//                }
//
//                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//                    holder.shadowImg.setImageDrawable(resource)
//                }
//            })
    }

    private fun createNewFlexItemTextView(context: Context,str:String): TextView {
        val textView = TextView(context)
        textView.gravity = Gravity.LEFT
        textView.text = str
        textView.textSize = 12f
        textView.setTextColor(Color.WHITE)
        val layoutParams = FlexboxLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val margin = SizeUtil.dp2Px(6)
        layoutParams.setMargins(0, 0, margin, 0)
        textView.layoutParams = layoutParams
        return textView
    }

    @DrawableRes
    fun getBackgroundColor(position: Int):Int{
        return when(position){
            0-> R.drawable.jb_red
            1-> R.drawable.jb_org
            2-> R.drawable.jb_yel
            3-> R.drawable.jb_gr
            4-> R.drawable.jb_bl
            5-> R.drawable.jb_d
            6-> R.drawable.jb_z
            else -> R.drawable.jb_red
        }
    }
    fun getPostionDay(position: Int):String{
        return when(position){
            0-> "星期日"
            1-> "星期一"
            2-> "星期二"
            3-> "星期三"
            4-> "星期四"
            5-> "星期五"
            6-> "星期六"
            else -> "？？？"
        }
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        var shadowImg:ImageSwitcher = v.findViewById(R.id.shadow_imag)
        val title: TextView = v.findViewById(R.id.tv_title)
        val flex_box :FlexboxLayout = v.findViewById(R.id.flex_box)
        init {
            switchInit()
            val params = shadowImg.layoutParams as ConstraintLayout.LayoutParams
            val windowManager = shadowImg.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val defaultDisplay = windowManager.defaultDisplay
            val point = Point()
            defaultDisplay.getSize(point)
            val x = point.x
            params.width = x - x/6 - SizeUtil.dp2Px(40)
            shadowImg.layoutParams = params
        }

        private fun switchInit(){
            shadowImg.inAnimation = AnimationUtils.loadAnimation(shadowImg.context,
                R.anim.fade_in)
            shadowImg.outAnimation = AnimationUtils.loadAnimation(shadowImg.context,
                R.anim.fade_out)
            shadowImg.setFactory{val i = ImageView(shadowImg.context)
                i.scaleType = ImageView.ScaleType.CENTER_CROP
                i.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                 i}
        }
    }
}