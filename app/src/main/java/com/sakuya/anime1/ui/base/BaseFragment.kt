package com.sakuya.anime1.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.size
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import me.yokeyword.fragmentation.SupportFragment

open class BaseFragment:SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LiveEventBus.get().with("colorful").observeStickyForever(Observer {
            Toast.makeText(context!!,container!!.size,Toast.LENGTH_SHORT).show()
        })
        return super.onCreateView(inflater, container, savedInstanceState)

    }


    override fun onDestroy() {
        super.onDestroy()

    }
}