package com.zoe.wan.android.http

/**
 * Http服务器解析
 */
data class HttpResult<out T>(
     val errorCode: String,
     val errorMsg: String,
     val data: T,
) {
     companion object {
          const val SUCCESS = "0"
          const val HTTP_SUCCESS = 200
          const val JSON_SYNTAX_ERROR = "10000"
     }
}
