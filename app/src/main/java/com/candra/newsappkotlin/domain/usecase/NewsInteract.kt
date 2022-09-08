package com.candra.newsappkotlin.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.candra.newsappkotlin.data.source.remote.States
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsInteract @Inject constructor(
    private val newsRepository: Repository
):NewsUseCase {
    override suspend fun getTopHeadlinesByCountryAndCategory(
        country: String,
        category: String
    ): LiveData<PagingData<NewsModel>> {
       return newsRepository.getTopHeadlinesByCountryAndCategory(country,category)
    }

    override fun searchEverythingFromNews(query: String): Flow<States<List<NewsModel>>> {
        return newsRepository.searchEverythingFromNews(query)
    }


    override suspend fun insertDataToFavorite(news: NewsModel) {
        newsRepository.insertDataToFavorite(news)
    }

    override suspend fun deleteFromFavoriteNews(news: NewsModel) {
        newsRepository.deleteFromFavoriteNews(news)
    }

    override fun isFavoriteNews(title: String): Flow<Boolean> {
       return newsRepository.isFavoriteNews(title)
    }

    override fun getAllFavoriteNews(): Flow<List<NewsModel>> {
        return newsRepository.getAllFavoriteNews()
    }
}