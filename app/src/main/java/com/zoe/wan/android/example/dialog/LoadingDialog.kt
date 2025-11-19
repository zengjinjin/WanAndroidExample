package com.zoe.wan.android.example.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.blankj.utilcode.util.ScreenUtils
import com.zoe.wan.android.example.R

class CommonDialog constructor(context: Context, theme: Int = R.style.CustomDialog) :
    Dialog(context, theme) {

    /**
     * show dialog
     * @param listener 事件监听
     * @param titleId 标题
     * @param contentId 描述
     * @param confirmTextId 确定
     * @param cancelTextId 取消
     * @param isSingle 是否单选
     */
    fun showConfirm(
        listener: OnItemClickListener?,
        @StringRes titleId: Int,
        @StringRes contentId: Int,
        @StringRes cancelTextId: Int = R.string.cancel,
        @StringRes confirmTextId: Int = R.string.confirm,
        isSingle: Boolean = false
    ) = this.showConfirm(
        listener,
        context.getString(titleId),
        context.getString(contentId),
        context.getString(cancelTextId),
        context.getString(confirmTextId),
        isSingle
    )

    /**
     * show dialog
     * @param listener 事件监听
     * @param title 标题
     * @param content 描述
     * @param confirmText 确定
     * @param cancelText 取消
     * @param isSingle 是否单选
     */
    fun showConfirm(
        listener: OnItemClickListener?,
        title: String,
        content: String,
        cancelText: String = context.getString(R.string.cancel),
        confirmText: String = context.getString(R.string.confirm),
        isSingle: Boolean = false
    ) {
        val view: View = LayoutInflater.from(context).inflate(R.layout.comm_dialog_layout, null)
        val cancelButton = view.findViewById<TextView>(R.id.cancel_button)
        val confirmButton = view.findViewById<TextView>(R.id.confirm_button)
        val tvTitle = view.findViewById<TextView>(R.id.title_textview)
        val tvContent = view.findViewById<TextView>(R.id.tvContent)
        tvTitle.text = title
        tvContent.text = content
        cancelButton.text = cancelText
        confirmButton.text = confirmText
        cancelButton.isVisible = !isSingle
        cancelButton.setOnClickListener {
            dismiss()
            listener?.onCancelClick()
        }
        confirmButton.setOnClickListener {
            dismiss()
            listener?.onConfirmClick()
        }

        show()
        setContentView(view)
        window?.apply {
            attributes.gravity = Gravity.CENTER
            attributes = attributes
            setLayout(
                ScreenUtils.getScreenHeight(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    interface OnItemClickListener {
        fun onCancelClick()

        fun onConfirmClick()
    }
}

@Volatile
private var progressDialog: ProgressDialog? = null
fun showTipLoading(context: Context, message: String, cancelable: Boolean = false) {
    if (progressDialog == null) {
        progressDialog = ProgressDialog(context)
    }
    progressDialog?.setCancelable(cancelable)
    progressDialog?.setMessage(message)
    progressDialog?.show()
}

fun dismissTipLoading() {
    if (progressDialog?.isShowing == true) {
        progressDialog?.cancel()
    }
    progressDialog = null
}