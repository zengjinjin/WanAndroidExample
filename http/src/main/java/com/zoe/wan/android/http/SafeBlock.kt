package com.zoe.wan.android.http

import android.os.Handler
import android.os.Looper


fun doMainSafe(block: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        block()
    } else Handler(Looper.getMainLooper()).post(block)
}