package com.zoe.wan.android.example.viewModel

import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.ItemEntity
import com.zoe.wan.android.example.repository.data.ListState
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModelState : LoadingVM() {

    private val _stateFlow = MutableStateFlow<ListState<ItemEntity>>(ListState())
    val stateFlow: StateFlow<ListState<ItemEntity>> = _stateFlow.asStateFlow()

    override fun request(isRefreshing: Boolean?) {
        viewModelScope.launch {
            if (_stateFlow.value.isRefreshing && _stateFlow.value.isLoading) return@launch

            val currentPage = if (true == isRefreshing) 0 else _stateFlow.value.curPage + 1
            _stateFlow.update {
                it.copy(
                    isLoading = true,
                    isRefreshing = true == isRefreshing,
                    error = null,
                    curPage = currentPage,
                )
            }

            try {
                coroutineScope {
                    var o1: Deferred<List<HomeBannerData>>? = null
                    if (true == isRefreshing) {
                        o1 = async(Dispatchers.IO, CoroutineStart.LAZY) {
                            Repository.getHomeBannerList()
                        }
                    }
                    val o2 = async(Dispatchers.IO) {
                        Repository.getHomeDataList("${_stateFlow.value.curPage}")
                    }
                    val banners = o1?.await().orEmpty()
                    val articles = o2.await()?.datas.orEmpty()
                    _stateFlow.update {
                        it.copy(
                            datas = if (true == isRefreshing) banners + articles else it.datas.orEmpty() + articles,
                            isLoading = false,
                            isRefreshing = true == isRefreshing,
                            hasMore = it.curPage != o2.await()?.pageCount
                        )
                    }
                }
            } catch (e: Exception) {
                _stateFlow.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = e,
                        curPage = if (true == isRefreshing) 0 else it.curPage - 1
                    )
                }
            }
        }
    }
}