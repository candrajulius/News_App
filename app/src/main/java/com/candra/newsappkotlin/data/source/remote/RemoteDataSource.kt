package com.candra.newsappkotlin.data.source.remote

import android.util.Log
import com.candra.newsappkotlin.data.source.remote.network.ApiInterface
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.domain.model.toGenereListArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiInterface
)
{

    companion object{
        const val TAG = "RemoteDataSource"
    }

    suspend fun getAllNewsByCountryAndCategory(country: String,category: String,page: Int,pageSize: Int)
    = apiService.topHeadlinesByCountryAndCategory(country,category, page = page, pageSize = pageSize)

    fun searchEveryThingFromNews(query: String) = flow<States<List<NewsModel>>> {
        emit(States.loading())
        val response = apiService.searchEverythingFromNews(query)
        response.let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.articles?.toGenereListArticle() ?: listOf()))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "searchEveryThingFromNews: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}