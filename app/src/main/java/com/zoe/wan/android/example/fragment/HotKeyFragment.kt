package com.zoe.wan.android.example.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.example.adapter.NewsAdapter
import com.zoe.wan.android.example.databinding.FragmentHotKeyBinding
import com.zoe.wan.android.example.structViewModel
import com.zoe.wan.android.example.viewModel.HotKeyViewModel
import com.zoe.wan.base.BaseFragment
import kotlinx.coroutines.launch

class HotKeyFragment : BaseFragment<FragmentHotKeyBinding>() {
    private val newsAdapter = NewsAdapter()

    override fun initViewData() {
//        val viewModel: HotKeyViewModel = structViewModel(HotKeyViewModel::class.java).apply {
//            refresh()
//        }
//        binding.rv.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = newsAdapter
//        }
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.homeDataList.collect { state ->
//                    if (state.loadMore) {
//                        binding.swipeRefreshLayout.finishLoadMore()
//                    } else {
//                        binding.swipeRefreshLayout.finishRefresh()
//                    }
//                    newsAdapter.setList(state.datas)
//                }
//            }
//        }
//        binding.swipeRefreshLayout.setOnRefreshListener {
//            viewModel.refresh()
//        }
//        binding.swipeRefreshLayout.setOnLoadMoreListener {
//            viewModel.loadMore()
//        }
    }
}