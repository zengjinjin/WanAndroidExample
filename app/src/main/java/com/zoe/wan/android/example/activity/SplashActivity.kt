package com.zoe.wan.android.example.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.zoe.wan.android.example.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        window.setBackgroundDrawable(null)
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
        // 模拟初始化延迟
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}