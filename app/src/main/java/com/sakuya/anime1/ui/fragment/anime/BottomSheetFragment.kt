package com.sakuya.anime1.ui.fragment.anime

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.DisplayMetrics
import android.widget.TextView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.sakuya.anime1.R


class BottomSheetFragment : SuperBottomSheetFragment(){

    lateinit var title:TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        initView(view)
        return view
    }

    fun initView(view:View){
        title = view.findViewById(R.id.textView)
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
}