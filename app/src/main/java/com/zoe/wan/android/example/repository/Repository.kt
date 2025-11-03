package com.zoe.wan.android.example.repository

import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.HomeBannerDataItem
import com.zoe.wan.android.example.repository.data.HomeListData
import com.zoe.wan.android.example.repository.data.ItemEntity
import com.zoe.wan.android.example.repository.data.KnowledgeListData
import com.zoe.wan.android.example.repository.data.KnowledgeListDetailData
import com.zoe.wan.android.http.HttpResult
import com.zoe.wan.android.http.RetrofitClient

object Repository {

    suspend fun getHomeBannerList(): List<HomeBannerData> {
        val data : HttpResult<List<HomeBannerDataItem>> = apiService.homeBannerList()
        return listOf(HomeBannerData(data = data.data))
    }

    suspend fun getHomeDataList(pageCount: String): HomeListData? {
        val data : HttpResult<HomeListData> = apiService.homeDataList(pageCount)
        return data.data?.apply {
            datas.map { it.itemType = ItemEntity.ITEM_LIST }
        }
    }

    suspend fun getKnowledgeDataList(): KnowledgeListData? {
        val data : HttpResult<KnowledgeListData>? = apiService.knowledgeList()
        return data?.data
    }

    suspend fun getKnowledgeDataListDetail(pageCount:String, cid: String): KnowledgeListDetailData? {
        val data : HttpResult<KnowledgeListDetailData>? = apiService.knowledgeListDetail(pageCount, cid)
        if (data?.data != null){
            return data.data
        }
        return null
    }

    private val apiService: ApiService by lazy {
        RetrofitClient.getInstance().getDefault(ApiService::class.java)
    }
}