package com.zoe.wan.base.view

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.app.Service
import android.content.Context
import android.content.ContextWrapper
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zoe.wan.base.R
import java.lang.ref.WeakReference

/**
 * loading dialog
 */
class LoadingDialog {

    companion object {
        private var mDialogReference: WeakReference<Dialog>? = null

        fun show(context: Context, flag: Boolean = true) {
            if (mDialogReference?.get()?.isShowing == true) {
                return
            }
    
            dismiss()
            val dialog = Dialog(context, R.style.LoadingDialogStyle)
            mDialogReference = WeakReference(dialog)

            dialog.setCancelable(flag)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(R.layout.dialog_loading)
            dialog.window?.setBackgroundDrawableResource(R.color.transparent)
            dialog.findViewById<ImageView>(R.id.iv).apply {
                Glide.with(context).load(R.drawable.loading).into(this)
            }

            if (isActive(dialog)) {
                dialog.show()
            }
        }

        fun dismiss() {
            val dialog = mDialogReference?.get()?: return
            if (dialog.isShowing && isActive(dialog)) {
                dialog.dismiss()
            }
            mDialogReference?.clear()
            mDialogReference = null
        }

        fun isShowing(): Boolean {
            val dialog = mDialogReference?.get()?: return false
            return dialog.isShowing && isActive(dialog)
        }

        /**
         * 校验当前上下文
         */
        private fun isActive(dialog: Dialog?): Boolean =
            getActivityFromContext(dialog?.context)?.let { !it.isDestroyed } ?: false

        private fun getActivityFromContext(context: Context?): Activity? {
            if (context == null) {
                return null
            }
            if (context is Activity) {
                return context
            }
            if (context is Application || context is Service) {
                return null
            }
            var c = context
            while (c != null) {
                if (c is ContextWrapper) {
                    c = c.baseContext
                    if (c is Activity) {
                        return c
                    }
                } else {
                    return null
                }
            }
            return null
        }
    }
}