package com.zoe.wan.android.example.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.blankj.utilcode.util.GsonUtils
import com.zoe.wan.android.example.databinding.FragmentHotKeyBinding
import com.zoe.wan.android.example.structViewModel
import com.zoe.wan.android.example.viewModel.HotKeyViewModel
import com.zoe.wan.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotKeyFragment : BaseFragment<FragmentHotKeyBinding>() {

    override fun initViewData() {
        val hotKeyViewModel = structViewModel(HotKeyViewModel::class.java)
        hotKeyViewModel.insertUsers()
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                hotKeyViewModel.livedata?.observe(this@HotKeyFragment) { users ->
                    users.forEach(fun(user) {
                        println("djj userinfo=" + GsonUtils.getGson().toJson(user))
                    })
                }
            }
        }
    }
}