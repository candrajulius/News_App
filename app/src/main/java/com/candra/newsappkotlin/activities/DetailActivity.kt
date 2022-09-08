package com.candra.newsappkotlin.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.candra.newsappkotlin.R
import com.candra.newsappkotlin.databinding.ActivityDetailLayoutBinding
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.helper.Constant
import com.candra.newsappkotlin.helper.Event
import com.candra.newsappkotlin.ui.viewmodel.DetailViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

// TODO: Add To Favorite
@AndroidEntryPoint
class DetailActivity: AppCompatActivity(){

    private lateinit var binding: ActivityDetailLayoutBinding
    private var newUrl = ""
    private var isFavorite = false
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        showLoading(true)
        receivedDataFromAdapter()

        with(binding){
            containerBack.setOnClickListener {
                onBackPressed()
            }
        }
        observerAllData()
    }

    private fun observerAllData(){
        detailViewModel.isFavorite.observe(this@DetailActivity){
            isFavorite = it
            binding.favoriteBtn.setFavorite(isFavorite)
        }
        detailViewModel.snackBarText.observe(this@DetailActivity,this::showSnackBar)
    }

    private fun setFavorite(news: NewsModel){
        binding.favoriteBtn.setOnClickListener {
            if (isFavorite) {
                detailViewModel.removeNewsFromFavorite(news,getString(R.string.remove),this@DetailActivity)
            }else{
                detailViewModel.insertNewsToFavorite(news,getString(R.string.succesfully),this@DetailActivity)
            }
        }
    }

    private fun FloatingActionButton.setFavorite(isStatusFavorite: Boolean)
    {
        if (isStatusFavorite) {
            this.setImageResource(R.drawable.ic_baseline_favorite_24)
//            this.imageTintList =
        }else this.setImageResource(R.drawable.ic_baseline_favorite_border)
    }



    private fun receivedDataFromAdapter(){
        intent.extras?.getParcelable<NewsModel>(Constant.EXTRA_NEWS)?.let { news ->
            with(binding){
                titleNews.apply {
                    text = news.title
                    isSelected = true
                    marqueeRepeatLimit = 30
                }

                newUrl = news.url

                webViewClient()

                containerShare.setOnClickListener {
                    val share = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        putExtra(Intent.EXTRA_TEXT,news.url)
                    }
                    startActivity(Intent.createChooser(share,"Share to: "))
                }

                detailViewModel.isFavoriteFromFavorite(news.title)

                setFavorite(news)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewClient(){
        binding.webViewDetail0.apply {
            with(settings){
                loadsImagesAutomatically = true
                javaScriptEnabled = true
                domStorageEnabled = true
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = true
                loadUrl(newUrl)
            }
            webViewClient = object: WebViewClient(){

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                   view?.loadUrl(newUrl)
                   return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    showLoading(false)
                    url.let { newUrl }
                }


                override fun onPageFinished(view: WebView?, url: String?) {
                    showLoading(false)
                    view?.loadUrl(newUrl)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.apply {
            lottieLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            webViewDetail0.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }



    private fun showSnackBar(eventMessage: Event<String>) {
        val message = eventMessage.getContentIfNotHandled() ?: return
        Snackbar.make(
            binding.parentData,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

}