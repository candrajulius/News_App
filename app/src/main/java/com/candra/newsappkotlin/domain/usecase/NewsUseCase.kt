package com.candra.newsappkotlin.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.candra.newsappkotlin.data.source.remote.States
import com.candra.newsappkotlin.domain.model.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {

    suspend fun getTopHeadlinesByCountryAndCategory(country: String,category: String): LiveData<PagingData<NewsModel>>

    fun searchEverythingFromNews(query: String): Flow<States<List<NewsModel>>>

    suspend fun insertDataToFavorite(news: NewsModel)

    suspend fun deleteFromFavoriteNews(news: NewsModel)

    fun isFavoriteNews(title: String): Flow<Boolean>

    fun getAllFavoriteNews(): Flow<List<NewsModel>>

}