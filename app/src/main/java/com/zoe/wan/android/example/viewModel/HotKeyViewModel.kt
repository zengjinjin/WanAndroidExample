package com.zoe.wan.android.example.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.HomeListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HotKeyViewModel : ViewModel() {

//    private val _homeDataList = MutableStateFlow(HomeListData())
//    val homeDataList: StateFlow<HomeListData> = _homeDataList.asStateFlow()
//
//    fun refresh() {
//        viewModelScope.launch {
//            _homeDataList.update {
//                it.copy(
//                    curPage = 0,
//                    isLoading = true,
//                    loadMore = false
//                )
//            }
//            try {
//                println("zjj refresh ${_homeDataList.value.curPage}")
//                val data = Repository.getHomeDataList("${_homeDataList.value.curPage}")
//                println("zjj data:" + data?.datas?.size)
//                _homeDataList.update {
//                    it.copy(
//                        datas = data?.datas,
//                        isLoading = false,
//                        loadMore = false
//                    )
//                }
//            } catch (e: Exception) {
//                println("zjj e:" + e.message)
//            }
//        }
//    }
//
//    fun loadMore() {
//        viewModelScope.launch {
//            _homeDataList.update {
//                var curPage = it.curPage
//                it.copy(
//                    curPage = ++curPage,
//                    isLoading = true,
//                    loadMore = true
//                )
//            }
//            try {
//                println("zjj loadMore ${_homeDataList.value.curPage}")
//                val data = Repository.getHomeDataList("${_homeDataList.value.curPage}")
//                println("zjj data:" + data?.datas?.size)
//                if (data != null) {
//                    // 加载更多：将新数据添加到现有列表中
//                    val currentList = _homeDataList.value?.datas ?: mutableListOf()
//                    val newList = data.datas?.let { currentList.plus(it) }
//                    println("zjj newList:" + newList?.size)
//                    _homeDataList.update {
//                        it.copy(
//                            datas = newList,
//                            isLoading = false,
//                            loadMore = true
//                        )
//                    }
//                } else {
//                    // 加载失败时回退 pageCount
//                }
//            } catch (e: Exception) {
//                println("zjj e:" + e.message)
//            }
//        }
//    }
}