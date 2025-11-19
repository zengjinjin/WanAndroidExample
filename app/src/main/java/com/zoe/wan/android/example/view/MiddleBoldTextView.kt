package com.zoe.wan.android.example.view

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 字体加粗
 * @author hz 2024-12-18
 */
class MiddleBoldTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        paint.strokeWidth = 1f
        paint.style = Paint.Style.FILL_AND_STROKE
    }
}