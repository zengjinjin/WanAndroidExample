package com.zoe.wan.base
import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.*

/**
 * 多语言国际化
 * @author 多金金 2025-11-19
 */
object LanguageManager {

    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    fun setLocale(context: Context, language: String): Context {
        persistLanguage(context, language)
        return updateResources(context, language)
    }

    private fun persistLanguage(context: Context, language: String) {
        val preferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        preferences.edit().putString(SELECTED_LANGUAGE, language).apply()
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        val displayMetrics = resources.displayMetrics

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setLocaleForApi24(configuration, locale)
            val c = context.createConfigurationContext(configuration)
            resources.updateConfiguration(configuration, displayMetrics)
            return c
        } else {
            setLocaleForLegacyApi(configuration, locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }

        return context
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun setLocaleForApi24(configuration: Configuration, locale: Locale) {
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
    }

    @Suppress("DEPRECATION")
    private fun setLocaleForLegacyApi(configuration: Configuration, locale: Locale) {
        configuration.locale = locale
    }

    fun getSavedLanguage(context: Context): String {
        val preferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        return preferences.getString(SELECTED_LANGUAGE, getSystemLanguage()) ?: getSystemLanguage()
    }

    private fun getSystemLanguage(): String {
        return Locale.getDefault().language
    }
}