package com.sakuya.anime1.ui.view.coloredshadow

import android.util.TypedValue
import android.view.View


internal fun View.dpToPx(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}