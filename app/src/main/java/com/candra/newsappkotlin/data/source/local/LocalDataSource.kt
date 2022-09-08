package com.candra.newsappkotlin.data.source.local

import com.candra.newsappkotlin.data.source.local.entity.NewsEntity
import com.candra.newsappkotlin.data.source.local.room.NewsDao
import com.candra.newsappkotlin.domain.model.NewsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val newsDao: NewsDao
){
    fun getAllFavoriteNews(): Flow<List<NewsEntity>> = newsDao.getAllFavoriteNews()
    suspend fun insertNewsToFavorite(newsEntity: NewsEntity) = newsDao.insertToFavorite(newsEntity)
    suspend fun deleteNewsFromFavorite(newsEntity: NewsEntity) = newsDao.deleteFromFavorite(newsEntity)
    fun isFavorite(title: String) = newsDao.isFavoriteNews(title)
}