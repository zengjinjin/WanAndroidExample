package com.zoe.wan.android.example.repository

import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.HomeBannerDataItem
import com.zoe.wan.android.example.repository.data.HomeListData
import com.zoe.wan.android.example.repository.data.KnowledgeListData
import com.zoe.wan.android.example.repository.data.KnowledgeListDetailData
import com.zoe.wan.android.http.ApiAddress.Article_List
import com.zoe.wan.android.http.ApiAddress.Home_Banner
import com.zoe.wan.android.http.ApiAddress.Knowledge_List
import com.zoe.wan.android.http.ApiAddress.Knowledge_List_detail
import com.zoe.wan.android.http.HttpResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("$Home_Banner")
    suspend fun homeBannerList() : HttpResult<List<HomeBannerDataItem>>

    @GET("$Article_List{pageCount}/json")
    suspend fun homeDataList(@Path("pageCount") pageCount: String) : HttpResult<HomeListData>

    @GET("$Knowledge_List")
    suspend fun knowledgeList() : HttpResult<KnowledgeListData>?

    @GET("$Knowledge_List_detail{pageCount}/json")
    suspend fun knowledgeListDetail(@Path("pageCount") pageCount: String, @Query("cid") cid: String) : HttpResult<KnowledgeListDetailData>?
}