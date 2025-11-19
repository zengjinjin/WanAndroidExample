package com.zoe.wan.android.example

import android.app.Application
import android.content.res.Configuration
import com.zoe.wan.base.LanguageManager

/**
 * @Description:
 * @Author: zengjinjin
 * @CreateDate: 2025/11/12
 */
class DjjApp: Application() {

    //冷启动会走onCreate()
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        // 应用启动时设置保存的语言
        println("zjj onCreate ${LanguageManager.getSavedLanguage(this)}")
        LanguageManager.setLocale(this, LanguageManager.getSavedLanguage(this))
    }

    //热启动会走onConfigurationChanged()
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        println("zjj onConfigurationChanged ${LanguageManager.getSavedLanguage(this)}")
        LanguageManager.setLocale(this, LanguageManager.getSavedLanguage(this))
    }

    companion object {
        lateinit var INSTANCE: DjjApp
    }
}