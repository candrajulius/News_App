package com.candra.newsappkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.candra.newsappkotlin.databinding.NewsLoadingBinding


class LoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<com.candra.newsappkotlin.ui.adapter.LoadStateAdapter.LoadingStateViewHolder>()
{

    inner class LoadingStateViewHolder(
        private val binding: NewsLoadingBinding,
        retry: () -> Unit
    ): RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState){
            with(binding){
                if (loadState is LoadState.Error){
                    errorMessage.text = loadState.error.localizedMessage
                }
                if (loadState is LoadState.Loading){
                    shimmerEffect.startShimmer()
                    shimmerEffect.visibility = View.VISIBLE
                }else{
                    shimmerEffect.hideShimmer()
                    shimmerEffect.visibility = View.GONE
                }
                btnRetry.isVisible = loadState is LoadState.Error
                errorMessage.isVisible = loadState is LoadState.Error
            }
        }
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
       return LoadingStateViewHolder(
           NewsLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false),
           retry
       )
    }


}