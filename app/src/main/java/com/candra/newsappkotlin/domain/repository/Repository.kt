package com.candra.newsappkotlin.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.candra.newsappkotlin.data.source.local.LocalDataSource
import com.candra.newsappkotlin.data.source.remote.NewsPagingSource
import com.candra.newsappkotlin.data.source.remote.RemoteDataSource
import com.candra.newsappkotlin.data.source.remote.States
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.helper.Convert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.PrivateKey
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): iRepository
{
    override suspend fun getTopHeadlinesByCountryAndCategory(
        country: String,
        category: String,
    ): LiveData<PagingData<NewsModel>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {NewsPagingSource(country,category,remoteDataSource)}
    ).liveData

    override fun searchEverythingFromNews(query: String): Flow<States<List<NewsModel>>> =
        remoteDataSource.searchEveryThingFromNews(query)

    override suspend fun insertDataToFavorite(news: NewsModel) {
       localDataSource.insertNewsToFavorite(
           Convert.mapDomainToEntityNews(news)
       )
    }

    override suspend fun deleteFromFavoriteNews(news: NewsModel) {
        localDataSource.deleteNewsFromFavorite(
            Convert.mapDomainToEntityNews(news)
        )
    }

    override fun isFavoriteNews(title: String): Flow<Boolean> =
        localDataSource.isFavorite(title)

    override fun getAllFavoriteNews(): Flow<List<NewsModel>> =
        localDataSource.getAllFavoriteNews().map {
            Convert.mapEntitesToDomainNews(it)
        }

}