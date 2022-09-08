package com.candra.newsappkotlin.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.candra.newsappkotlin.R
import com.candra.newsappkotlin.data.source.remote.States
import com.candra.newsappkotlin.databinding.ActivityMainBinding
import com.candra.newsappkotlin.databinding.SearchFragmentBottomSheetBinding
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.helper.Constant
import com.candra.newsappkotlin.ui.adapter.ViewPagerAdapter
import com.candra.newsappkotlin.helper.Constant.TAB_TITTLES
import com.candra.newsappkotlin.ui.adapter.SearchAdapter
import com.candra.newsappkotlin.ui.fragment.*
import com.candra.newsappkotlin.ui.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
// TODO: Built of feature Search
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val searchAdapter by lazy { SearchAdapter(::onClickData) }
    private val searchViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTablelayouts()
        setToolbar()

        binding.containerImageSearch.setOnClickListener {
            showDialogBottomSheet()
        }
        binding.btnFavorite.setOnClickListener {
            startActivity(Intent(this@MainActivity,FavoriteActivity::class.java))
        }
    }

    private fun showDialogBottomSheet(){
        val dialogBottomSheet = Dialog(this)
        val dialogBinding = SearchFragmentBottomSheetBinding.inflate(layoutInflater)
        dialogBottomSheet.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogBinding.root)
        }
        with(dialogBinding){
            edtSearch.addTextChangedListener {
                val lowercase = it.toString().lowercase()
                if (lowercase.isEmpty()){
                    showTextEmpty(true,mtvTextEmpty,rvNewsSearch,progressBarSearch)
                }else{
                    showTextEmpty(false,mtvTextEmpty,rvNewsSearch,progressBarSearch)
                    observerAlLData(lowercase,rvNewsSearch,progressBarSearch,mtvTextEmpty)
                }
            }
            setAdapter(rvNewsSearch)
            containerCardView.setOnClickListener {
                dialogBottomSheet.dismiss()
            }
        }

        dialogBottomSheet.apply {
            show()
            window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                setBackgroundDrawable(ColorDrawable(Color.WHITE))
                attributes.windowAnimations = R.style.DialogAnimation
                setGravity(Gravity.BOTTOM)
            }
        }
    }

    private fun observerAlLData(query: String,rvSearch: RecyclerView,progressBarSearch: ProgressBar,mtvTextEmpty: MaterialTextView){
        searchViewModel.searchValueList(query).observe(this){ data ->
            populateNews(data,mtvTextEmpty,progressBarSearch,rvSearch)
        }
    }

    private fun showTextEmpty(isTextEmpty: Boolean,mtvEmptyText: MaterialTextView,rvNewSearch: RecyclerView,progressBarSearch: ProgressBar){
        mtvEmptyText.visibility = if (isTextEmpty) View.VISIBLE else View.GONE
        rvNewSearch.visibility = if (isTextEmpty) View.GONE else View.GONE
        progressBarSearch.visibility = if (isTextEmpty) View.GONE else View.GONE
    }

    private fun showLoading(isLoading: Boolean,progressBarSearch: ProgressBar,rvSearch: RecyclerView,mtvTextEmpty: MaterialTextView){
        progressBarSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
        rvSearch.visibility = if (isLoading) View.GONE else View.VISIBLE
        mtvTextEmpty.visibility = if (isLoading) View.GONE else View.GONE
    }

    private fun populateNews(it: States<List<NewsModel>>,mtvTextEmpty: MaterialTextView,progressBarSearch: ProgressBar,rvSearch: RecyclerView){
        when(it){
            is States.Loading -> {showLoading(true,progressBarSearch,rvSearch,mtvTextEmpty)}
            is States.Success -> {
                showLoading(false,progressBarSearch,rvSearch,mtvTextEmpty)
                searchAdapter.temptAllData(it.data)
            }
            is States.Failed -> {showTextEmpty(true,mtvTextEmpty, rvSearch,progressBarSearch)}
            else -> {}
        }
    }

    private fun setAdapter(rvSearch: RecyclerView){
        rvSearch.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            adapter = searchAdapter
        }
    }

    private fun onClickData(news: NewsModel)
    {
        Intent(this,DetailActivity::class.java).apply {
            putExtra(Constant.EXTRA_NEWS,news)
        }.also { startActivity(it) }
    }
   private fun setToolbar(){
        supportActionBar?.hide()
    }

     private fun setTablelayouts(){
         val fragmentList = arrayListOf(
            HomeFragment(),
            BusinessFragment(),
            Entertainment(),
            ScienceFragment(),
            HealthFragment(),
            SportsFragment(),
            TechnologyFragment()
         )

        val adapterPagerMain = ViewPagerAdapter(this,fragmentList)
        binding.viewPagerMain.adapter = adapterPagerMain

        binding.apply {
            TabLayoutMediator(tabsMain,viewPagerMain){tabs,position ->
                tabs.text = resources.getString(TAB_TITTLES[position])
            }.attach()
        }
     }
}