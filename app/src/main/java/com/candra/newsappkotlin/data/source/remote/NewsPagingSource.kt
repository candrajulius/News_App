package com.candra.newsappkotlin.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.domain.model.toGenereListArticle
import com.candra.newsappkotlin.helper.Constant
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val country: String,
    private val category: String,
    private val remoteDataSource: RemoteDataSource
): PagingSource<Int,NewsModel>(){

    override fun getRefreshKey(state: PagingState<Int, NewsModel>): Int? =
        state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsModel> {
        return try {
            val position = params.key ?: Constant.INITIAL_PAGE_INDEX
            val responseData = remoteDataSource.getAllNewsByCountryAndCategory(country,category,position,params.loadSize)
            .body()?.articles?.toGenereListArticle() ?: listOf()

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == Constant.INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        }catch (exception: Exception){
            return LoadResult.Error(exception)
        }
    }

}