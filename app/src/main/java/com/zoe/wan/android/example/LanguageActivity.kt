package com.zoe.wan.android.example

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zoe.wan.android.example.databinding.ActivityLanguageBinding
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.base.LanguageManager

//多语言国际化
class LanguageActivity : BaseActivity<ActivityLanguageBinding>() {

    private lateinit var adapter: LanguageAdapter
    private val languages = listOf(
        Language("en", "English", "English"),
        Language("zh", "中文", "Chinese"),
//        Language("ja", "日本語", "Japanese"),
//        Language("es", "Español", "Spanish"),
//        Language("fr", "Français", "French")
    )

    override fun initViewData() {
        setContentView(R.layout.activity_language)

        setupRecyclerView()
        println("zjj LanguageActivity onCreate")
        // 确保资源更新
//        updateConfiguration()
//        LanguageManager.setLocale(this, LanguageManager.getSavedLanguage(this))
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewLanguages)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val currentLanguage = LanguageManager.getSavedLanguage(this)
        adapter = LanguageAdapter(languages, currentLanguage) { language ->
            setAppLanguage(language.code)

            startActivity(Intent(this@LanguageActivity, TestActivity::class.java))
            finish()
        }

        recyclerView.adapter = adapter
    }

}

data class Language(val code: String, val displayName: String, val englishName: String)

class LanguageAdapter(
    private val languages: List<Language>,
    private val selectedLanguage: String,
    private val onLanguageSelected: (Language) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewLanguage: TextView = itemView.findViewById(R.id.textViewLanguage)
        val imageViewSelected: ImageView = itemView.findViewById(R.id.imageViewSelected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_language, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val language = languages[position]
        val isSelected = language.code == selectedLanguage

        holder.textViewLanguage.text = language.displayName
        holder.imageViewSelected.visibility = if (isSelected) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            onLanguageSelected(language)
        }
    }

    override fun getItemCount(): Int = languages.size
}