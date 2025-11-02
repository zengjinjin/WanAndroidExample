package com.zoe.wan.android.example.viewModel

import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.data.KnowledgeListData
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.KnowledgeListDetailDataItem
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class KnowledgeViewMode : BaseViewModel() {

    val kownledgeList = SingleLiveEvent<KnowledgeListData>()
    val kownledgeDetailList = SingleLiveEvent<List<KnowledgeListDetailDataItem>>()

    fun getKownledgeList(){
        viewModelScope.launch {
            kownledgeList.postValue(Repository.getKnowledgeDataList())
        }
    }

    fun getKownledgeDetailList(pageCount:String, cid: String){
        viewModelScope.launch {
            kownledgeDetailList.postValue(Repository.getKnowledgeDataListDetail(pageCount, cid)?.datas)
        }
    }
}