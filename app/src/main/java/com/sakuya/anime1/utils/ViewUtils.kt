package com.sakuya.anime1.utils

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout


object ViewUtils {

    open fun setCollapsingToolbarLayoutFlag(appBarLayout: CollapsingToolbarLayout, flags:Int){
        val layoutParams = appBarLayout.layoutParams as (AppBarLayout.LayoutParams)
        layoutParams.scrollFlags = flags
        appBarLayout.layoutParams = layoutParams
    }

    open fun setCollapsingToolbarLayoutFlag(appBarLayout: AppBarLayout){
        val layoutParams = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.height = SizeUtil.dp2Px(60)
        appBarLayout.layoutParams = layoutParams
    }
}
