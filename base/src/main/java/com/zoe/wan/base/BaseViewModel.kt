package com.zoe.wan.base

import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.zoe.wan.base.view.LoadingDialog

abstract class BaseViewModel: ViewModel() {

    /***
     * 显示loading
     */
    fun showLoading(flag: Boolean = false) = LoadingDialog.show(ActivityUtils.getTopActivity(), flag)

    /**
     * 结束loading
     */
    fun dismissLoading() = LoadingDialog.dismiss()

    /**
     * 结束loading
     */
//    fun dismissGlobalLoading() = launchMain { dismissLoading() }

}
