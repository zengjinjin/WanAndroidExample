package com.zoe.wan.android.example.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerAdapter
import com.zoe.wan.android.example.repository.data.HomeBannerDataItem

class ImageAdapter(mDatas: List<HomeBannerDataItem>?) :
    BannerAdapter<HomeBannerDataItem, ImageAdapter.BannerViewHolder>(mDatas) {
    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder,
        data: HomeBannerDataItem,
        position: Int,
        size: Int
    ) {
        Glide.with(holder.imageView.context).load(data.imagePath).into(holder.imageView)
    }

    inner class BannerViewHolder(@param:NonNull var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)
}