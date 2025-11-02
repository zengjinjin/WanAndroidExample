package com.zoe.wan.android.example.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zoe.wan.android.example.viewModel.KnowledgeViewMode
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.activity.KnowledgeDetailActivity
import com.zoe.wan.android.example.databinding.FragmentKnowledgeBinding
import com.zoe.wan.android.example.repository.data.KnowledgeListDataItem
import com.zoe.wan.android.example.structViewModel
import com.zoe.wan.android.repository.data.DetailIntentList
import com.zoe.wan.android.repository.data.DetailTabIntentData
import com.zoe.wan.base.BaseFragment

class KnowledgeFragment : BaseFragment<FragmentKnowledgeBinding>() {
    private lateinit var adapter : BaseQuickAdapter<KnowledgeListDataItem, BaseViewHolder>

    override fun initViewData() {
        val viewModel = structViewModel(KnowledgeViewMode::class.java).apply {
            getKownledgeList()
        }
        binding.rv.layoutManager = LinearLayoutManager(context)
        adapter = object : BaseQuickAdapter<KnowledgeListDataItem, BaseViewHolder>(R.layout.knowledge_item){
            override fun convert(holder: BaseViewHolder, item: KnowledgeListDataItem) {
                holder.setText(R.id.title, item.name)
                var sb = StringBuilder()
                item.children.forEach {
                    sb.append("${it.name}  ")
                }
                holder.setText(R.id.subTitle, sb.toString())
            }
        }
        // 设置 item 点击事件
        adapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as KnowledgeListDataItem
            //初始化传递的数据
            val intentList: DetailIntentList
            val tabList = mutableListOf<DetailTabIntentData>()

            item?.let {
                it.children?.forEach { c ->
                    tabList.add(DetailTabIntentData(c?.name ?: "", "${c?.id}"))
                    LogUtils.d("知识体系：name=${c?.name} id=${c?.id}")
                }
            }
            intentList = DetailIntentList(tabList)

            //存到bundle里通过intent传递过去
            val intent = Intent(context, KnowledgeDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(KnowledgeDetailActivity.INTENT_TABS_LIST, intentList)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.rv.adapter = adapter
        viewModel.kownledgeList.observe(this){
            list -> adapter.setList(list)
        }
    }
}