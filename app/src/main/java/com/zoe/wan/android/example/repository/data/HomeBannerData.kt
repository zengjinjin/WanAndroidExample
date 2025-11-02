package com.zoe.wan.android.example.repository.data

import com.chad.library.adapter.base.entity.MultiItemEntity

data class HomeBannerData(
    var datas: List<HomeBannerDataItem>?,
    override val itemType: Int = ITEM_BANNER
) : ItemEntity()

abstract class ItemEntity: MultiItemEntity {

    companion object {
        const val ITEM_BANNER = 0
        const val ITEM_LIST = 1
    }
}

data class HomeBannerDataItem(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String,
)