package com.zoe.wan.android.example.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.zoe.wan.android.example.databinding.ActivityKnowledgeDetailBinding
import com.zoe.wan.android.example.databinding.TabItemBinding
import com.zoe.wan.android.example.fragment.KnowledgeDetailFragment
import com.zoe.wan.android.repository.data.DetailIntentList
import com.zoe.wan.android.repository.data.DetailTabIntentData
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.base.adapter.Pager2Adapter

class KnowledgeDetailActivity : BaseActivity<ActivityKnowledgeDetailBinding>() {

    companion object {
        const val INTENT_TABS_LIST = "INTENT_TABS_LIST"
        const val INTENT_BUNDLE = "INTENT_BUNDLE"
    }

    private fun initPageModule(list: List<DetailTabIntentData>) {
        val pageFragList = mutableListOf<Fragment>()
        list.forEach { data ->
            pageFragList.add(KnowledgeDetailFragment(name = data.name, cid = data.id))
        }
        val pageAdapter = Pager2Adapter(this)
        pageAdapter.setData(pageFragList)
        //默认不做预加载Fragment
        binding?.detailViewPager2?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding?.detailViewPager2?.adapter = pageAdapter
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initViewData() {
        binding?.knowledgeDetailBack?.setOnClickListener {
            finish()
        }
        //获取intent数据
        var intentList: DetailIntentList? = null
        val bundle: Bundle? = intent.extras
        bundle?.let {
            intentList = it.getParcelable(INTENT_TABS_LIST, DetailIntentList::class.java)
        }

        //先初始化ViewPager2
        initPageModule(intentList?.list ?: emptyList())

        //tablayout与viewpager2绑定
        TabLayoutMediator(
            binding?.detailTabLayout!!,
            binding?.detailViewPager2!!,
            true,
            true
        ) { tab, position ->
            val binding = TabItemBinding.inflate(
                layoutInflater,
                null,
                false
            )
            binding.tabItemTitle.text = intentList?.list?.get(position)?.name
            tab.customView = binding.root
        }.attach()
    }

}