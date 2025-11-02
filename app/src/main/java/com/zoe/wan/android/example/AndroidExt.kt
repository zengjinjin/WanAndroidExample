package com.zoe.wan.android.example

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/** 构建 ViewModel */
fun <T : ViewModel> ViewModelStoreOwner.structViewModel(
    clazz: Class<T>
): T = ViewModelProvider(this)[clazz]

fun View?.addChildClickViewIds(call: View.OnClickListener, @IdRes vararg viewIds: Int){
    for (viewId in viewIds) {
        this?.findViewById<View>(viewId)?.setOnClickListener(call)
    }
}

/**
 *  设置需要点击事件的子view
 *  @param call 事件监听
 *  @Params:viewIds - IntArray
 */
fun AppCompatActivity.addChildClickViewIds(call: View.OnClickListener, @IdRes vararg viewIds: Int) {
    for (viewId in viewIds) {
        findViewById<View>(viewId)?.setOnClickListener(call)
    }
}