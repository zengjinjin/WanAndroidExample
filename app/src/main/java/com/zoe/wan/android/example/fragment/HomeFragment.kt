package com.zoe.wan.android.example.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.zoe.wan.android.example.viewModel.HomeViewModel
import com.zoe.wan.android.example.adapter.NewsAdapter
import com.zoe.wan.android.example.databinding.FragmentHomeBinding
import com.zoe.wan.android.example.structViewModel
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.android.example.repository.data.Result
import com.zoe.wan.android.example.viewModel.HomeViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var myAdapter: NewsAdapter
    private val isState = false

    override fun initViewData() {
        val viewModel = structViewModel(
            if (isState) HomeViewModelState::class.java
            else HomeViewModel::class.java
        ).apply {
            binding.swipeRefreshLayout.postDelayed(
                { binding.swipeRefreshLayout.autoRefresh() },
                200L
            )
        }
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsAdapter().also { myAdapter = it }
        }
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.request(true)
            }
            setOnLoadMoreListener {
                viewModel.request(false)
            }
//            viewModel.request(null) //普通页面加载传null即可
        }

        if (viewModel is HomeViewModel) {
            viewModel.liveData.observe(this) { liveData ->
                viewModel.handleLoadingAndError(liveData)
                var hasMore = true
                if (liveData is Result.Success) {
                    hasMore = liveData.data.size >= 10
                    myAdapter.setList(liveData.data)
                }
                when {
                    binding.swipeRefreshLayout.state == RefreshState.Refreshing ->
                        binding.swipeRefreshLayout.finishRefresh()
                    hasMore ->
                        binding.swipeRefreshLayout.finishLoadMore()
                    !hasMore ->
                        binding.swipeRefreshLayout.finishLoadMoreWithNoMoreData()
                    else -> {}
                }
            }
        }

        if (viewModel is HomeViewModelState) {
            lifecycleScope.launch(Dispatchers.Main) {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.stateFlow.collect { state ->
                        myAdapter.setList(state.datas)
                        viewModel.handleLoadingAndError(state)
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