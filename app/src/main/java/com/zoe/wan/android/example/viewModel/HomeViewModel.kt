package com.zoe.wan.android.example.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.Result
import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.ItemEntity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeViewModel : LoadingVM() {

    val liveData = MutableLiveData<Result<List<ItemEntity>>>()
    private val source: MutableList<ItemEntity> = mutableListOf()
    private var pageCount: Int = 0

    override fun request(isRefreshing: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
            liveData.postValue(Result.Loading)
            try {
                val data = coroutineScope {
                    var o1: Deferred<List<HomeBannerData>>? = null
                    if (true == isRefreshing) {
                        pageCount = 0
                        o1 = async {
                            Repository.getHomeBannerList()
                        }
                    } else {
                        pageCount++
                    }
                    val o2 = async {
                        Repository.getHomeDataList("$pageCount")
                    }
                    val banners = o1?.await().orEmpty()
                    val articles = o2.await()?.datas.orEmpty()
                    Result.Success(
                        if (true == isRefreshing) {
                            source.clear()
                            source.addAll(banners + articles)
                            source
                        } else {
                            source.addAll(articles)
                            source
                        }
                    )
                }
                liveData.postValue(data)
            } catch (e: Exception) {
                println("hzz 错误${e.message}")
                liveData.postValue(Result.Error(e))
                pageCount--
            }
        }
    }
}