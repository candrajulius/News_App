package com.candra.newsappkotlin.helper

import android.content.Context
import android.text.format.DateFormat
import com.candra.newsappkotlin.R
import com.candra.newsappkotlin.data.source.local.entity.NewsEntity
import com.candra.newsappkotlin.domain.model.NewsModel
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

object Convert {

    fun mapEntitesToDomainNews(input: List<NewsEntity>): List<NewsModel>{
        return input.map {
            NewsModel(
                author = it.author,
                content = it.content,
                description = it.desciption,
                publishedAt = it.publishedAt,
                title = it.title,
                url = it.url,
                urlToImage = it.urlToImage,
                isFavorite = it.isFavorite
            )
        }
    }

    fun mapDomainToEntityNews(input: NewsModel) = NewsEntity(
        id = 0,
        author = input.author,
        content = input.content,
        desciption = input.description,
        publishedAt = input.publishedAt,
        title = input.title,
        url = input.url,
        urlToImage = input.urlToImage,
        isFavorite = input.isFavorite

    )

    fun Context.formatContentEventSnackBarText(title: String): String =
        String.format(this.getString(R.string.news_favorite_event),title)

    val String.timeHoursAgo get(): String{
        val apiFormat = SimpleDateFormat(Constant.DATE_FORMAT_API,Locale.getDefault()).parse(this)
        return PrettyTime(Locale.getDefault()).format(apiFormat).toString()
    }

    val String.dateFormat get(): String {
        val apiFormat = SimpleDateFormat(Constant.DATE_FORMAT_API,Locale.getDefault()).parse(this)
        return DateFormat.format(Constant.DATE_FORMAT_LOCAL,apiFormat).toString()
    }
}