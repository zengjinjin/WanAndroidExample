package com.zoe.wan.android.example.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.example.Address
import com.zoe.wan.android.example.Person
import com.zoe.wan.android.example.viewModel.HomeViewModel
import com.zoe.wan.android.example.adapter.NewsAdapter
import com.zoe.wan.android.example.databinding.FragmentHomeBinding
import com.zoe.wan.android.example.structViewModel
import com.zoe.wan.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var adapter: NewsAdapter

    override fun initViewData() {
        val viewModel = structViewModel(HomeViewModel::class.java)
        adapter = NewsAdapter()
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listState.collect { list ->
                    list?.let {
                        adapter.setList(list.datas)
                    }
                }
            }
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getHomeDataList (true)
        }
        binding.swipeRefreshLayout.setOnLoadMoreListener {
            viewModel.getHomeDataList (false)
        }
    }
}