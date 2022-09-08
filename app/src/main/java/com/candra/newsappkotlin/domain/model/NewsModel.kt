package com.candra.newsappkotlin.domain.model

import android.os.Parcelable
import com.candra.newsappkotlin.data.source.remote.response.Article
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsModel(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val isFavorite: Boolean
): Parcelable

fun List<Article>.toGenereListArticle(): MutableList<NewsModel>{
    val listArticle = mutableListOf<NewsModel>()
    this.forEach { listArticle.add(it.toArticle()) }
    return listArticle
}

fun Article.toArticle(): NewsModel = NewsModel(
    author = this.author ?: "Author is null",
    content = this.content ?: "Content is null",
    description = this.description ?: "Description is null",
    publishedAt = this.publishedAt ?: "PublishedAt is null",
    title = this.title ?: "Title is null",
    url = this.url ?: "Url is null",
    urlToImage = this.urlToImage ?: "Url Image is null",
    isFavorite = false
)
