package com.zoe.wan.android.example

import android.widget.Toast
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Description:
 * @Author: zengjinjin
 * @CreateDate: 2025/11/12
 */
object ErrorHandler {

    fun handlerError(e: Throwable) {
        when (e) {
            is UnknownHostException -> {
                when {
                    !DjjApp.INSTANCE.isNetworkAvailable() -> Toast.makeText(DjjApp.INSTANCE, "网络不可用，请检查网络连接", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(DjjApp.INSTANCE, "无法连接到服务器，请检查网络设置或稍后重试", Toast.LENGTH_SHORT).show()
                }
            }

            is IllegalArgumentException -> {
                Toast.makeText(DjjApp.INSTANCE, "请求参数错误", Toast.LENGTH_SHORT).show()
            }

            is IOException -> {
                Toast.makeText(DjjApp.INSTANCE, "网络异常，请稍后重试", Toast.LENGTH_SHORT).show()
            }

            is SocketTimeoutException -> {
                Toast.makeText(DjjApp.INSTANCE, "请求超时，请检查网络连接", Toast.LENGTH_SHORT).show()
            }

            is ConnectException -> {
                Toast.makeText(DjjApp.INSTANCE, "无法连接到服务器", Toast.LENGTH_SHORT).show()
            }

            is HttpException -> {
                when (e.code()) {
                    400 -> Toast.makeText(DjjApp.INSTANCE, "请求参数错误", Toast.LENGTH_SHORT).show()
                    401 -> Toast.makeText(DjjApp.INSTANCE, "未授权，请重新登录", Toast.LENGTH_SHORT).show()
                    403 -> Toast.makeText(DjjApp.INSTANCE, "访问被禁止", Toast.LENGTH_SHORT).show()
                    404 -> Toast.makeText(DjjApp.INSTANCE, "请求的资源不存在", Toast.LENGTH_SHORT).show()
                    500 -> Toast.makeText(DjjApp.INSTANCE, "服务器内部错误", Toast.LENGTH_SHORT).show()
                    503 -> Toast.makeText(DjjApp.INSTANCE, "服务不可用", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(DjjApp.INSTANCE, "网络错误: ${e.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            else -> {
                Toast.makeText(DjjApp.INSTANCE, "未知错误: " + e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}