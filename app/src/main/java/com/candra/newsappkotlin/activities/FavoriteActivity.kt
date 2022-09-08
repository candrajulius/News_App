package com.candra.newsappkotlin.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.newsappkotlin.databinding.ActivityFavoriteBinding
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.helper.Constant
import com.candra.newsappkotlin.ui.adapter.AllAdapter
import com.candra.newsappkotlin.ui.adapter.SearchAdapter
import com.candra.newsappkotlin.ui.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity()
{

    private lateinit var binding: ActivityFavoriteBinding
    private val adapterAll by lazy { SearchAdapter(::onClick) }
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setAdapterData()
        observerAllData()
        binding.containerCardClose.setOnClickListener {
            onBackPressed()
        }
    }

    private fun onClick(itemNews: NewsModel)
    {
        Intent(this@FavoriteActivity,DetailActivity::class.java).apply {
            putExtra(Constant.EXTRA_NEWS,itemNews)
        }.also { startActivity(it) }
    }

    private fun observerAllData(){
        favoriteViewModel.getAllFavoriteNews.observe(this,this::populateNews)
    }

    private fun populateNews(it: List<NewsModel>) {
        adapterAll.temptAllData(it)
    }

    private fun setAdapterData(){
        binding.apply {
            rvFavorite.apply {
                layoutManager = LinearLayoutManager(this@FavoriteActivity,LinearLayoutManager.VERTICAL,false)
                adapter = adapterAll
            }
        }
    }

}