package com.zoe.wan.android.example

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Description:
 * @Author: zengjinjin
 * @CreateDate: 2025/11/17
 */
class EditTextAdapter(
    private var items: MutableList<EditTextItem>,
    private val onTextChanged: (Int, String) -> Unit
) : BaseQuickAdapter<EditTextItem, BaseViewHolder>(R.layout.item_edittext, items) {

    override fun convert(holder: BaseViewHolder, item: EditTextItem) {
        val editText = holder.getView<EditText>(R.id.editText)
        // 设置提示文本
        editText.hint = item.hint

        // 设置文本内容，避免重复触发TextWatcher
//        if (editText.text.toString() != item.text) {
//            editText.setText(item.text)
//        }
        editText.setText(item.text)

        // 设置文本变化监听器
//        editText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(s: Editable?) {
//                // 使用绝对位置而不是position参数，因为position可能已经改变
//                val currentPosition = holder.adapterPosition
//                if (currentPosition != RecyclerView.NO_POSITION) {
//                    onTextChanged(currentPosition, s.toString())
//                }
//            }
//        })

        // 设置焦点变化监听，用于处理输入完成后的操作
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val currentPosition = holder.adapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    item.text = editText.text.toString()
                }
            }
        }
    }

    // 更新数据的方法
    fun updateItems(newItems: List<EditTextItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    // 添加新项目的方法
    fun addItem(item: EditTextItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    // 删除项目的方法
    fun removeItem(position: Int) {
        if (position in 0 until items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}