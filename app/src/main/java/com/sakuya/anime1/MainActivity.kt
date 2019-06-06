package com.sakuya.anime1

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.sakuya.anime1.helper.AnimeHelper
import com.sakuya.anime1.http.HttpFactory
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup


class MainActivity : GSYBaseActivityDetail<StandardGSYVideoPlayer>() {

    val animeHelper = AnimeHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        //initVideoBuilderMode()
        val list = animeHelper.getInstance().getMainData()
        list.forEach {
            Log.e("name",it.id)
        }
    }

    override fun clickForFullScreen() {

    }

    override fun getDetailOrientationRotateAuto(): Boolean {
       return true
    }

    override fun getGSYVideoPlayer(): StandardGSYVideoPlayer {
        return detail_player
    }

    override fun getGSYVideoOptionBuilder(): GSYVideoOptionBuilder {
        return GSYVideoOptionBuilder()
            .setUrl("https://miku4.anime1.app/510/8.mp4")
            .setCacheWithPlay(true)
            .setVideoTitle(" ")
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setSeekRatio(1f)
    }
}
