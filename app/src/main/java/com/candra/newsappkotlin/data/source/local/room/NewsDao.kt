package com.candra.newsappkotlin.data.source.local.room

import androidx.room.*
import com.candra.newsappkotlin.data.source.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao{
    @Query("select * from news_table")
    fun getAllFavoriteNews(): Flow<List<NewsEntity>>

    @Delete
    suspend fun deleteFromFavorite(newsEntity: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavorite(newsEntity: NewsEntity)

    @Query("select exists(select * from news_table where title = :titleNews)")
    fun isFavoriteNews(titleNews: String): Flow<Boolean>

//    @Query("delete from news_table")
//    suspend fun deleteAllFavorite(): Flow<>
}