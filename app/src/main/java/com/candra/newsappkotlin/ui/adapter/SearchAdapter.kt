package com.candra.newsappkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.newsappkotlin.R
import com.candra.newsappkotlin.databinding.ItemListNewsBinding
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.helper.Convert.dateFormat
import com.candra.newsappkotlin.helper.Convert.timeHoursAgo
import com.candra.newsappkotlin.helper.ViewUtils.contentImage

class SearchAdapter(
    val onItemCallback : (NewsModel) -> Unit
): RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

    inner class ViewHolder(
        private val binding: ItemListNewsBinding
    ): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: NewsModel){
            with(binding){
                textTitleNews.text = data.title
                textDate.text = data.publishedAt.dateFormat
                textTime.text = data.publishedAt.timeHoursAgo
                if (data.urlToImage == "Url is null") imgContent.setImageResource(R.drawable.ic_baseline_broken_image_24)
                else imgContent.contentImage(data.urlToImage)
                root.setOnClickListener {
                    onItemCallback(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        return ViewHolder(
            ItemListNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
       holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<NewsModel>(){
        override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem.title == newItem.title
        }
    })

    fun temptAllData(listItem: List<NewsModel>){
        differ.submitList(listItem)
    }

}