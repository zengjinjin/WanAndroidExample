package com.zoe.wan.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * 使用 ViewBinding + ViewModel 的 Activity 基类
 * @param VB : ViewBinding 类型
 * @param VM : ViewModel 类型
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    /**
     * 初始化数据
     */
    abstract fun initViewData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        setContentView(binding.root)
        initViewData()
    }

    /**
     * 自动初始化 ViewBinding
     */
    @Suppress("UNCHECKED_CAST")
    private fun initViewBinding() {
        try {
            val superClass = javaClass.genericSuperclass
            val type = (superClass as ParameterizedType).actualTypeArguments[0]
            val classBinding = type as Class<VB>
            val method = classBinding.getMethod("inflate", android.view.LayoutInflater::class.java)
            binding = method.invoke(null, layoutInflater) as VB
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("自动初始化 ViewBinding 失败，请检查泛型参数: ${e.message}")
        }
    }

    fun setAppLanguage(languageCode: String) {
        // 更新应用语言
        LanguageManager.setLocale(this, languageCode)
        // 重启Activity以应用语言更改
        recreate()
        // 可选：显示成功消息
        Toast.makeText(this, getString(R.string.language_changed), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 如果 ViewBinding 需要手动清理
        if (::binding.isInitialized) {
            // 某些情况下可能需要清理引用
        }
    }
}