package com.zoe.wan.android.example.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.ItemEntity
import com.zoe.wan.android.example.repository.data.ListState
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _listState = MutableStateFlow<ListState<ItemEntity>>(ListState())
    val listState: StateFlow<ListState<ItemEntity>> = _listState.asStateFlow()

    private val pageSize = 20

    init {
        loadInitialData()
    }

    // 初始加载
    private fun loadInitialData() {
        viewModelScope.launch {
            _listState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    curPage = 0
                )
            }

            try {
                val o1 = async(Dispatchers.IO) {
                    Repository.getHomeBannerList()
                }
                val o2 = async(Dispatchers.IO) {
                    Repository.getHomeDataList("${_listState.value.curPage}")
                }
                val banners = o1.await()
                val articles = o2.await()?.datas.orEmpty()
                _listState.update {
                    it.copy(
                        datas = banners + articles,
                        isLoading = false,
                        curPage = 0,
                    )
                }
            } catch (e: Exception) {
                _listState.update {
                    it.copy(
                        isLoading = false,
                        error = "加载失败: ${e.message}"
                    )
                }
            }
        }
    }


    // 下拉刷新加载更多
    internal fun getHomeDataList(isRefreshing: Boolean) {
        viewModelScope.launch {
            if (_listState.value.isRefreshing && _listState.value.isLoading) return@launch

            val currentPage = if (isRefreshing) 0 else _listState.value.curPage + 1
            _listState.update {
                it.copy(
                    isLoading = true,
                    isRefreshing = isRefreshing,
                    error = null,
                    curPage = currentPage,
                )
            }

            try {
                var o1: Deferred<List<HomeBannerData>>? = null
                if (isRefreshing) {
                    o1 = async(Dispatchers.IO, CoroutineStart.LAZY) {
                        Repository.getHomeBannerList()
                    }
                }
                val o2 = async(Dispatchers.IO) {
                    Repository.getHomeDataList("${_listState.value.curPage}")
                }
                val banners = o1?.await().orEmpty()
                val articles = o2.await()?.datas.orEmpty()
                _listState.update {
                    it.copy(
                        datas = if (isRefreshing) banners + articles else it.datas.orEmpty() + articles,
                        isLoading = false,
                        isRefreshing = isRefreshing,
                        curPage = currentPage
                    )
                }
            } catch (e: Exception) {
                _listState.update {
                    it.copy(
                        isRefreshing = false,
                        error = "刷新失败: ${e.message}",
                        curPage = if (isRefreshing) 0 else it.curPage - 1
                    )
                }
            }
        }
    }
}