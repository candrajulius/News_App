package com.candra.newsappkotlin.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.newsappkotlin.R
import com.candra.newsappkotlin.activities.DetailActivity
import com.candra.newsappkotlin.databinding.ItemListNewsBinding
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.helper.Constant
import com.candra.newsappkotlin.helper.Convert.dateFormat
import com.candra.newsappkotlin.helper.Convert.timeHoursAgo
import com.candra.newsappkotlin.helper.ViewUtils.contentImage

@RequiresApi(Build.VERSION_CODES.M)
class AllAdapter(
    private val context: Context
): PagingDataAdapter<NewsModel,AllAdapter.ViewHolder>(DIFF_CALLBACK){

    inner class ViewHolder(private val binding: ItemListNewsBinding): RecyclerView.ViewHolder(binding.root)
    {

        fun bind(news: NewsModel){
            with(binding){
                if (news.urlToImage == "Url is null") imgContent.setImageResource(R.drawable.ic_baseline_broken_image_24)
                else imgContent.contentImage(news.urlToImage)
                textTitleNews.text = news.title
                textDate.text = news.publishedAt.dateFormat
                textTime.text = news.publishedAt.timeHoursAgo

                containerCardView.setOnClickListener {
                    val optionSelected: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        Pair(textTitleNews,"title")
                    )
                    context.startActivity(
                        Intent(context,DetailActivity::class.java).apply {
                            putExtra(Constant.EXTRA_NEWS,news)
                        },optionSelected.toBundle()
                    )
                }
            }
        }
    }

    override fun onBindViewHolder(holder: AllAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(news = it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllAdapter.ViewHolder {
        return ViewHolder(
            ItemListNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    companion object{
        val DIFF_CALLBACK = object:DiffUtil.ItemCallback<NewsModel>(){
            override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean =
                oldItem.title == newItem.title

        }
    }

}