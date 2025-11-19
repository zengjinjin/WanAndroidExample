package com.zoe.wan.android.example

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

/** 构建 ViewModel */
fun <T : ViewModel> ViewModelStoreOwner.structViewModel(
    clazz: Class<T>
): T = ViewModelProvider(this)[clazz]

fun View?.addChildClickViewIds(call: View.OnClickListener, @IdRes vararg viewIds: Int){
    for (viewId in viewIds) {
        this?.findViewById<View>(viewId)?.setOnClickListener(call)
    }
}

// 显示 Toast 的扩展函数
fun Context.showToast(message: String) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        // 如果在主线程，直接显示
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    } else {
        // 如果在后台线程，切换到主线程
        MainScope().launch {
            Toast.makeText(this@showToast, message, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Context.showLongToast(message: String) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        // 如果在主线程，直接显示
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    } else {
        // 如果在后台线程，切换到主线程
        MainScope().launch {
            Toast.makeText(this@showLongToast, message, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
}

fun Context.getAppVersion(): String {
    return try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
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

/**
 * 对象深拷贝
 * @return 拷贝后对象
 * @throws RuntimeException
 */
@Throws(RuntimeException::class)
fun Any?.deepCopy(): Any? {
    if (this !is Serializable) return null
    val memoryBuffer = ByteArrayOutputStream()
    var out: ObjectOutputStream? = null
    var `in`: ObjectInputStream? = null
    val dist: Any?
    try {
        out = ObjectOutputStream(memoryBuffer)
        out.writeObject(this@deepCopy)
        out.flush()
        `in` = ObjectInputStream(ByteArrayInputStream(memoryBuffer.toByteArray()))
        dist = `in`.readObject()
    } catch (e: Exception) {
        throw RuntimeException(e)
    } finally {
        try {
            out?.close()
        } catch (e: IOException) {
            //ignore
        }
        try {
            `in`?.close()
        } catch (e: IOException) {
            //ignore
        }
    }
    return dist
}