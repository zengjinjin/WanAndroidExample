package com.zoe.wan.android.http

import java.io.Serializable

data class BaseResponse<out T>(
    val errorMsg: String?,
    //errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
    //errorCode = -1001 代表登录失效，需要重新登录。
    val errorCode: Int?,
    val data: T?,
)

