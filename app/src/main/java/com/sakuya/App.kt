package com.sakuya

import android.app.Application
import android.util.Log
import android.widget.Toast

import cn.bmob.v3.Bmob
import com.orhanobut.hawk.Hawk

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        Bmob.initialize(this,"d303d4c853adfc969a9afebee70a53e9")
        Hawk.init(this).build()
        setting()
    }

    fun setting(){
        if(!Hawk.contains("isColorful"))
            Hawk.put("isColorful",false)
        if(!Hawk.contains("card_color"))
            Hawk.put("card_color","#7a7a7a")
        if(!Hawk.contains("colors")){
            Hawk.put("colors", arrayOf("#7a7a7a","#4f3a65","#e4007c","#6b48ff","#12d3cf","#e41749","#211717","#ffc785","#a9eec2"))
        }

    }
}