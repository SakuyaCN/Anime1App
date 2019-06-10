package com.sakuya.anime1.utils

import android.content.res.Resources
import android.util.TypedValue

object SizeUtil {

    fun dp2Px(dp: Int): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        return Math.round(dp * displayMetrics.density)
    }

    fun px2sp(pxValue: Float): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}