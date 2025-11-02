package com.zoe.wan.android.example.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.zoe.wan.android.example.R

/**
 * 字体自适应
 * @author hz 2024-12-18
 */
open class AutoSizeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {
    
    private var defaultTextSize: Float = 0f
    
    /**
     * 默认最小字体大小
     */
    private var minTextSize: Float
    
    init {
        defaultTextSize = textSize
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoSizeTextView)
        minTextSize = typedArray.getDimension(R.styleable.AutoSizeTextView_autoSizeMinTextSize, 10f)
        typedArray.recycle()
    }
    
    /**
     * 设置最小字体大小
     */
    fun setMinTextSize(minSize: Float) {
        this.minTextSize = minSize
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        adjustTextSize()
    }
    
    private fun adjustTextSize() {
        val text = text.toString()
        if (text.isEmpty()) return
        val maxWidth = measuredWidth - paddingLeft - paddingRight
        val textPaint = paint
        var textSize = defaultTextSize
        
        while (textPaint.measureText(text) > maxWidth && textSize > minTextSize) {
            textSize--
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }
        
        // 如果最终字体大小小于最小值，则设置为最小值
        if (textSize < minTextSize) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, minTextSize)
        }
    }
}