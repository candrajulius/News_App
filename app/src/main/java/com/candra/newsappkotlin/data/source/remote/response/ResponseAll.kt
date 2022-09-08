package com.candra.newsappkotlin.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class ResponseAll(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)