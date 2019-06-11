package com.sakuya.anime1

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.sakuya.anime1.helper.AnimeHelper
import com.sakuya.anime1.http.HttpFactory
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import com.jeremyliao.liveeventbus.LiveEventBus
import com.sakuya.anime1.entity.AnimeEntity
import com.sakuya.anime1.entity.AnimeEntityList
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.player.SystemPlayerManager
import tv.danmaku.ijk.media.exo2.ExoSourceManager


class MainActivity : AppCompatActivity() {

    val animeHelper = AnimeHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        setContentView(R.layout.activity_main)
        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)
        ExoSourceManager.setSkipSSLChain(true)
        //initVideoBuilderMode()
        val list = animeHelper.getInstance().getMainData()
        detail_player.setUp("https://i.animeone.me/MthjE.m3u8",false,"")
        detail_player.startPlayLogic()


    }

    fun observe(){
        LiveEventBus.get()
            .with("main_data", AnimeEntityList::class.java)
            .observeForever(observer)
    }

    val observer = Observer<AnimeEntityList> {
        it.list.forEach {
            Log.i("aaaaaa",it.title)
        }
    }

//    override fun clickForFullScreen() {
//
//    }
//
//    override fun getDetailOrientationRotateAuto(): Boolean {
//       return true
//    }
//
//    override fun getGSYVideoPlayer(): StandardGSYVideoPlayer {
//        return detail_player
//    }
//
//    override fun getGSYVideoOptionBuilder(): GSYVideoOptionBuilder {
//        return GSYVideoOptionBuilder()
//            //.setUrl("https://miku4.anime1.app/510/8.mp4")
//            .setUrl("https://i.animeone.me/8ZpC7.m3u8")
//            .setCacheWithPlay(false)
//            .setVideoTitle(" ")
//            .setIsTouchWiget(true)
//            .setRotateViewAuto(false)
//            .setLockLand(false)
//            .setShowFullAnimation(false)
//            .setNeedLockFull(true)
//            .setSeekRatio(1f)
//    }
}
