package com.candra.newsappkotlin.data.source.remote.network

import com.candra.newsappkotlin.BuildConfig.API_KEY
import com.candra.newsappkotlin.data.source.remote.response.ResponseAll
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines")
    suspend fun topHeadlinesByCountryAndCategory(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseAll>

    @GET("everything")
    suspend fun searchEverythingFromNews(
        @Query("q")query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<ResponseAll>

}