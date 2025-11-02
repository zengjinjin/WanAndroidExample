package com.zoe.wan.android.example.activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.ActivityMainBinding
import com.zoe.wan.android.example.fragment.HomeFragment
import com.zoe.wan.android.example.fragment.HotKeyFragment
import com.zoe.wan.android.example.fragment.KnowledgeFragment
import com.zoe.wan.android.example.fragment.PersonalFragment
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.base.adapter.Pager2Adapter
import com.zoe.wan.base.tab.NavigationBottomBar


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initViewData() {
        initPages()
        val icons = arrayListOf<Bitmap>(
            BitmapFactory.decodeResource(resources, R.drawable.icon_home_selected),
            BitmapFactory.decodeResource(resources, R.drawable.icon_hot_key_selected),
            BitmapFactory.decodeResource(resources, R.drawable.icon_knowledge_selected),
            BitmapFactory.decodeResource(resources, R.drawable.icon_personal_selected)
        )

        val iconsGray = arrayListOf<Bitmap>(
            BitmapFactory.decodeResource(resources, R.drawable.icon_home_grey),
            BitmapFactory.decodeResource(resources, R.drawable.icon_hot_key_grey),
            BitmapFactory.decodeResource(resources, R.drawable.icon_knowledge_grey),
            BitmapFactory.decodeResource(resources, R.drawable.icon_personal_grey)
        )

        val tabText = arrayListOf("首页", "热点", "体系", "个人")
        binding?.navigationBottomBar?.setSelectedIcons(icons)
        binding?.navigationBottomBar?.setUnselectIcons(iconsGray)
        binding?.navigationBottomBar?.setTabText(tabText)
        binding?.navigationBottomBar?.setupViewpager(binding.viewPager)
        binding?.navigationBottomBar?.start()
        binding?.navigationBottomBar?.registerTabClickListener(object : NavigationBottomBar.OnBottomTabClickListener{
            override fun tabClick(position: Int) {

            }
        })
    }

    private fun initPages(){
        val tabPages = mutableListOf<Fragment>()
        tabPages.add(HomeFragment())
        tabPages.add(HotKeyFragment())
        tabPages.add(KnowledgeFragment())
        tabPages.add(PersonalFragment())
        val pageAdapter = Pager2Adapter(this)
        pageAdapter.setData(tabPages)
        binding.viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding.viewPager.adapter = pageAdapter
    }
}