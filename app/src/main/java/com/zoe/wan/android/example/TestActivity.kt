package com.zoe.wan.android.example

import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zoe.wan.android.example.databinding.ActivityTestBinding
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.base.LanguageManager

class TestActivity : BaseActivity<ActivityTestBinding>() {

    private lateinit var adapter: EditTextAdapter
    private val itemList = mutableListOf<EditTextItem>()

    override fun initViewData() {
        setContentView(R.layout.activity_test)

        initData()
        setupRecyclerView()
        setupClickListeners()
    }

    private fun initData() {
        // 初始化一些示例数据
        for (i in 1..5) {
            itemList.add(EditTextItem(
                id = i,
                hint = "请输入第 $i 项内容"
            ))
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // 设置布局管理器
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 设置适配器
        adapter = EditTextAdapter(itemList) { position, text ->
            // 当EditText内容变化时的回调
//            itemList[position].text = text
//            if (recyclerView.isComputingLayout) {
//                recyclerView.post { adapter.notifyItemChanged(position) }
//            } else {
//                // 更新item编号显示
//                adapter.notifyItemChanged(position)
//            }
        }

        recyclerView.adapter = adapter
    }

    private fun setupClickListeners() {
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            // 添加新项目
            val newItem = EditTextItem(
                id = itemList.size + 1,
                hint = "请输入新内容"
            )
            adapter.addItem(newItem)
        }

        findViewById<Button>(R.id.btnShowData).setOnClickListener {
            // 显示所有数据
//            showAllData()
            startActivity(Intent(this@TestActivity, LanguageActivity::class.java))
        }
    }

    private fun showAllData() {
        val stringBuilder = StringBuilder()
        stringBuilder.append("当前所有数据:\n\n")

        itemList.forEachIndexed { index, item ->
            stringBuilder.append("项目 ${index + 1}: ${item.text.ifEmpty { "[空]" }}\n")
        }

        AlertDialog.Builder(this)
            .setTitle("数据预览")
            .setMessage(stringBuilder.toString())
            .setPositiveButton("确定", null)
            .show()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setAppLanguage(LanguageManager.getSavedLanguage(this))
    }
}