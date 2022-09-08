package com.candra.newsappkotlin.helper

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.candra.newsappkotlin.R
import com.facebook.shimmer.ShimmerFrameLayout

object ViewUtils {


    fun populateLoading(isLoading: Boolean,rvHome: RecyclerView,shimmerEffect: ShimmerFrameLayout)
    {
        if (isLoading){
            shimmerEffect.apply {
                startShimmer()
                visibility = View.VISIBLE
            }
            rvHome.visibility = View.GONE
        }else {
            shimmerEffect.apply {
                hideShimmer()
                visibility = View.GONE
            }
            rvHome.visibility = View.VISIBLE
        }

    }

    fun ImageView.contentImage(urlToImage: String){
        this.load(urlToImage){
            crossfade(true)
            crossfade(600)
            error(R.drawable.ic_baseline_broken_image_24)
            placeholder(R.drawable.ic_baseline_image_24)
        }
    }

}