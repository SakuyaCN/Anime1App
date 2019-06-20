package com.sakuya.anime1.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.*
import android.widget.PopupWindow
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sakuya.anime1.R


object ViewUtils {

    interface IviewP {
        fun setView(view: View, popupWindow: PopupWindow)

    }
    interface Iview2 {
        fun setView(view: View, dialog: Dialog)
    }
    interface IviewBSD {
        fun setView(view: View, dialog: BottomSheetDialog)
    }

    fun showPW(
        activity: Activity, viewXml: Int, parent: View, alpha: Boolean,
        iview: IviewP
    ) {// 显示POP

        val popupWindow: PopupWindow
        val view = LayoutInflater.from(activity).inflate(viewXml, null)

        popupWindow = PopupWindow(
            view, ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, true
        )

        popupWindow.isTouchable = true// 点击空白处的时候PopupWindow会消失
        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(
            BitmapDrawable(
                activity
                    .resources, null as Bitmap?
            )
        )

        popupWindow.showAsDropDown(parent, Gravity.CENTER_HORIZONTAL, 25, 0)
        if (alpha) {
            // 设置背景颜色变暗
            val lp = activity.window.attributes
            lp.alpha = 0.7f
            //此行代码主要是解决在华为手机上半透明效果无效的bug
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            activity.window.attributes = lp
            popupWindow.setOnDismissListener {
                val lp = activity.window.attributes
                lp.alpha = 1f
                activity.window.attributes = lp
            }
        }
        iview.setView(view, popupWindow)
    }

    fun showD(activity: Context, viewXml: Int, iview: Iview2,outCancel:Boolean) {// 对话框
        val mAlertDialog: Dialog = Dialog(activity, R.style.AlertDialogStyle)

        val view = LayoutInflater.from(activity).inflate(viewXml, null)
        mAlertDialog.setContentView(view)
        mAlertDialog.setCanceledOnTouchOutside(outCancel)
        mAlertDialog.show()
        iview.setView(view, mAlertDialog)
    }

    fun showBSD(activity: Context, viewXml: Int, iview: IviewBSD,outCancel:Boolean) {// 底部对话框
        val bsdDialog = BottomSheetDialog(activity)
        bsdDialog.setCanceledOnTouchOutside(outCancel)
        val view = LayoutInflater.from(activity).inflate(viewXml, null)
        iview.setView(view, bsdDialog)
    }
}
