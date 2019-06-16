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
        if(!Hawk.contains("isColorful")){
            Hawk.put("isColorful",false)
        }
    }
}