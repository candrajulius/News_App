package com.candra.newsappkotlin.helper

import androidx.annotation.StringRes
import com.candra.newsappkotlin.R

object Constant{

    @StringRes
    val TAB_TITTLES = intArrayOf(
        R.string.home,
        R.string.business,
        R.string.entertainment,
        R.string.science,
        R.string.health,
        R.string.sports,
        R.string.technology
    )

    const val INITIAL_PAGE_INDEX = 1
    const val DATE_FORMAT_API = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val DATE_FORMAT_LOCAL = "MMMM, dd yyyy"
    const val ID = "id"
    const val HOME = "general"
    const val EXTRA_NEWS = "extra_news"
    const val HEALTH = "health"
    const val BUSINESS = "business"
    const val ENTERTAINMENT = "entertainment"
    const val SCIENCE = "science"
    const val SPORTS = "sports"
    const val TECHNOLOGY = "technology"
    const val TAG = "DEBUG"


}