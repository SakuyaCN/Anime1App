package com.sakuya.anime1.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.Fade
import android.transition.TransitionInflater
import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sakuya.anime1.AnimeActivity
import com.sakuya.anime1.R

class LoadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(FLAG_FULLSCREEN,
            FLAG_FULLSCREEN)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        val explode = Fade()
        explode.duration = 500
        window.enterTransition = explode
        window.exitTransition = explode
        setContentView(R.layout.acvicity_load)
        Handler().postDelayed({
            AnimeActivity.finishActivity()
            startActivity(Intent(this,AnimeActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        },1000)
        Handler().postDelayed({
            finish()
        },2000)
    }
    override fun onKeyDown(keyCode:Int, event: KeyEvent):Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> return true
        }
        return super.onKeyDown(keyCode, event)
    }


}
