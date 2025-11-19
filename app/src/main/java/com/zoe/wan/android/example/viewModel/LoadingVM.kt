package com.zoe.wan.android.example.viewModel

import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.zoe.wan.android.example.ErrorHandler
import com.zoe.wan.android.example.dialog.dismissTipLoading
import com.zoe.wan.android.example.dialog.showTipLoading
import com.zoe.wan.android.example.repository.data.ListState
import com.zoe.wan.android.example.repository.data.Result

/**
 * @Description:统一网络错误处理和Loading状态
 * @Author: zengjinjin
 * @CreateDate: 2025/11/13
 */
abstract class LoadingVM: ViewModel() {

    /**
     * 定义请求
     */
    abstract fun request(isRefreshing: Boolean?)

    /**
     * 处理Loading和网络异常
     * livedata
     */
    fun <T> handleLoadingAndError(liveData: Result<T>) {
        when(liveData) {
            is Result.Loading -> {
                showTipLoading(ActivityUtils.getTopActivity(), "玩命加载中")
            }
            is Result.Success -> {
                dismissTipLoading()
            }
            is Result.Error -> {
                dismissTipLoading()
                ErrorHandler.handlerError(liveData.exception)
            }
        }
    }

    /**
     * 处理Loading和网络异常
     * state flow
     */
    fun <R> handleLoadingAndError(state: ListState<R>) {
        if (state.isLoading) {
            showTipLoading(ActivityUtils.getTopActivity(), "玩命加载中")
            return
        } else dismissTipLoading()
        if (!state.error?.message.isNullOrEmpty()) {
            ErrorHandler.handlerError(state.error!!)
        }
    }

}