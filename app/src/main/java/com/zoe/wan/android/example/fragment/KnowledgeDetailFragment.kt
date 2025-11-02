package com.zoe.wan.android.example.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.FragmentKnowledgeBinding
import com.zoe.wan.android.example.repository.data.KnowledgeListDetailDataItem
import com.zoe.wan.android.example.structViewModel
import com.zoe.wan.android.example.viewModel.KnowledgeViewMode
import com.zoe.wan.base.BaseFragment

class KnowledgeDetailFragment(val name: String, val cid: String) : BaseFragment<FragmentKnowledgeBinding>() {

    lateinit var adapter : BaseQuickAdapter<KnowledgeListDetailDataItem, BaseViewHolder>

    override fun initViewData() {
        val viewMode = structViewModel(KnowledgeViewMode::class.java)
        viewMode.getKownledgeDetailList("0", cid)
        binding.rv.layoutManager = LinearLayoutManager(context)
        adapter = object : BaseQuickAdapter<KnowledgeListDetailDataItem, BaseViewHolder>(R.layout.knowledge_detail_item){
            override fun convert(holder: BaseViewHolder, item: KnowledgeListDetailDataItem) {
                var name = if (item?.author?.isEmpty()==true){
                    item?.shareUser
                }else{
                    item?.author
                }
                holder.setText(R.id.itemKnowledgeTitle, name)
                holder.setText(R.id.itemKnowledgeSubTitle, item.title)
            }
        }
        binding.rv.adapter = adapter
        viewMode.kownledgeDetailList.observe(this){
            list -> adapter.setList(list)
        }
    }
}