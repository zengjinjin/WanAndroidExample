package com.zoe.wan.android.example.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.example.viewModel.HomeViewModel
import com.zoe.wan.android.example.adapter.NewsAdapter
import com.zoe.wan.android.example.databinding.FragmentHomeBinding
import com.zoe.wan.android.example.structViewModel
import com.zoe.wan.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var myAdapter: NewsAdapter

    override fun initViewData() {
        val viewModel = structViewModel(HomeViewModel::class.java).apply {
            binding.swipeRefreshLayout.postDelayed(
                { binding.swipeRefreshLayout.autoRefresh() },
                200
            )
        }
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsAdapter().also { myAdapter = it }
        }
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.getHomeDataList(true)
            }
            setOnLoadMoreListener {
                viewModel.getHomeDataList(false)
            }
        }
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listState.collect { state ->
                    state?.let {
                        myAdapter.setList(state.datas)
                        if (!state.error.isNullOrEmpty()) {
                            showToast(state.error)
                        }
                        when {
                            state.isRefreshing -> binding.swipeRefreshLayout.finishRefresh()
                            state.hasMore -> binding.swipeRefreshLayout.finishLoadMore()
                            else -> binding.swipeRefreshLayout.finishLoadMoreWithNoMoreData()
                        }
                    }
                }
            }
        }
    }
}