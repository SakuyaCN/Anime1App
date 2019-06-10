package com.sakuya.anime1.helper

import android.util.Log
import com.jeremyliao.liveeventbus.LiveEventBus
import com.sakuya.anime1.entity.AnimeEntity
import com.sakuya.anime1.entity.AnimeEntityList
import com.sakuya.anime1.http.HttpFactory
import io.reactivex.functions.Consumer
import org.jsoup.Jsoup
import java.util.concurrent.CountDownLatch

class AnimeHelper private constructor(){

    companion object{
        fun getInstance() = Holder.instance
    }

    private object Holder{
        val instance = AnimeHelper()
    }

    fun getMainData(){
        val list = arrayListOf<AnimeEntity>()
        HttpFactory.getMain(Consumer {
            val doc = Jsoup.parse(it.string())
            val elements = doc.getElementById("recent-posts-6").select("li")
            elements.forEach {
                list.add(AnimeEntity(it.select("a").attr("href"),it.text()))
            }
            LiveEventBus.get().with("main_data").post(AnimeEntityList(list))

        }, Consumer {

        })
    }
}