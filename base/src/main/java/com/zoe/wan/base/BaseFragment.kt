package com.zoe.wan.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * 完全自动化的 BaseFragment
 * 自动初始化 ViewBinding 和 ViewModel，子类无需任何初始化代码
 *
 * @param VB : ViewBinding ViewBinding 类型
 * @param VM : ViewModel ViewModel 类型
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    // ViewBinding 实例
    protected lateinit var binding: VB

    // 用于 Fragment 生命周期的安全绑定
    private var _binding: VB? = null
    protected val safeBinding get() = _binding!!

    // ==================== 可选重写的方法 ====================

    /**
     * 观察 LiveData - 可选实现
     * 在这里观察 ViewModel 的 LiveData/StateFlow
     */
    abstract fun initViewData()

    /**
     * 获取 ViewModel Factory - 可选重写
     * 用于带参数的 ViewModel
     */
    open fun getViewModelFactory(): ViewModelProvider.Factory? = null

    /**
     * 是否使用 Activity 级别的 ViewModel - 可选重写
     * 返回 true 则与 Activity 共享 ViewModel 实例
     */
    open fun useActivityViewModel(): Boolean = false

    /**
     * 是否在 onDestroyView 时清理绑定 - 可选重写
     * 默认 true，对于某些场景可以返回 false 保持绑定
     */
    open fun shouldCleanUpBinding(): Boolean = true

    // ==================== 核心初始化逻辑 ====================

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 自动初始化 ViewBinding
        initViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 调用初始化方法
        initViewData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 清理 ViewBinding 引用，避免内存泄漏
        if (shouldCleanUpBinding()) {
            _binding = null
        }
    }

    // ==================== 私有初始化方法 ====================

    /**
     * 自动初始化 ViewBinding
     */
    @Suppress("UNCHECKED_CAST")
    private fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?) {
        try {
            val superClass = javaClass.genericSuperclass
            val type = (superClass as ParameterizedType).actualTypeArguments[0]
            val classBinding = type as Class<VB>
            val method = classBinding.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
            binding = method.invoke(null, inflater, container, false) as VB
            _binding = binding
        } catch (e: Exception) {
            handleInitError("ViewBinding", e)
        }
    }

    /**
     * 处理初始化错误
     */
    private fun handleInitError(type: String, e: Exception) {
        e.printStackTrace()
        throw RuntimeException(
            "自动初始化 $type 失败\n" +
                    "请检查：\n" +
                    "1. 是否正确指定了泛型参数 <ViewBindingType, ViewModelType>\n" +
                    "2. ViewModel 是否有默认构造函数或提供了 Factory\n" +
                    "3. ViewBinding 类是否生成了 inflate 方法\n" +
                    "错误详情: ${e.message}"
        )
    }

    // ==================== 工具方法 ====================

    protected inline fun <reified T : ViewModel> getActivityViewModel(): T {
        return ViewModelProvider(requireActivity())[T::class.java]
    }

    /**
     * 显示 Toast 的便捷方法
     */
    protected fun showToast(message: String) {
        android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToast(message: String) {
        android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_LONG).show()
    }
}