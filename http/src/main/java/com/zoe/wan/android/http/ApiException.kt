package com.zoe.wan.android.http

data class ApiException(val errorCode: String, val errorMsg: String): RuntimeException(errorMsg)

