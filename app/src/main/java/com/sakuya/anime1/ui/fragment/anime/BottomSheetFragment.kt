package com.sakuya.anime1.ui.fragment.anime

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.DisplayMetrics
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.sakuya.anime1.R
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.util.Log
import com.airbnb.lottie.LottieAnimationView

class BottomSheetFragment : SuperBottomSheetFragment(){

    private var top_view: LottieAnimationView?= null
    @SuppressLint("ObjectAnimatorBinding")
    override fun EXPANDED() {
        if(top_view!=null) {
            val animator = ObjectAnimator.ofFloat(top_view, "rotation",180f)
            animator.duration = 300
            animator.start()
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun COLLAPSED() {
        if(top_view!=null) {
            val animator = ObjectAnimator.ofFloat(top_view, "rotation",0f)
            animator.duration = 300
            animator.start()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_all, container, false)
        initView(view)
        return view
    }

    fun initView(view:View){
        top_view = view.findViewById(R.id.top_view)
    }

    override fun isSheetCancelableOnTouchOutside(): Boolean {
        return false
    }

    override fun getPeekHeight(): Int {
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        //        int width = displayMetrics.widthPixels;
        return height * 2 / 3
    }

    override fun getStatusBarColor(): Int = Color.WHITE

    override fun onDestroy() {
        super.onDestroy()
        Log.e("BottomSheetBehavior", "onDestroy")
    }
}