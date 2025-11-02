package com.zoe.wan.android.example.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.HomeBannerDataItem
import com.zoe.wan.android.example.repository.data.HomeListData
import com.zoe.wan.android.example.repository.data.HomeListItemData
import com.zoe.wan.android.example.repository.data.ItemEntity


class NewsAdapter : BaseMultiItemQuickAdapter<ItemEntity, BaseViewHolder>() {

    init {
        addItemType(ItemEntity.ITEM_BANNER, R.layout.home_banner)
        addItemType(ItemEntity.ITEM_LIST, R.layout.news_item)
    }

    override fun convert(holder: BaseViewHolder, multiItemEntity: ItemEntity) {
        if (holder.itemViewType == ItemEntity.ITEM_BANNER) {
            val item: HomeBannerData? = multiItemEntity as? HomeBannerData
            val banner = holder.getView<Banner<HomeBannerDataItem, ImageAdapter>>(R.id.banner)
            holder.setText(R.id.banner_title, item?.datas?.get(0)?.title)
            banner.setAdapter(ImageAdapter(item?.datas))
                .setIndicator(CircleIndicator(banner.context))
                .addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {

                    }

                    override fun onPageSelected(position: Int) {
                        holder.setText(R.id.banner_title, item?.datas?.get(position)?.title)
                    }

                    override fun onPageScrollStateChanged(state: Int) {

                    }

                })

        } else {
            val item: HomeListItemData? = multiItemEntity as? HomeListItemData
            holder.setText(R.id.news_title, item?.title)
                .setText(R.id.news_chapterName, item?.chapterName)
                .setText(R.id.news_author, item?.author)
                .setText(R.id.news_date, item?.niceShareDate)
                .setBackgroundResource(
                    R.id.iv_collect,
                    if (item?.collect == true) R.drawable.img_collect else R.drawable.img_collect_grey
                )
        }
    }
}