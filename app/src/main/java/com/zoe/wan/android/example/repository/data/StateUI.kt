package com.zoe.wan.android.example.repository.data

data class ListState<T>(
    val datas: List<T>? = listOf(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val curPage: Int = 0
)