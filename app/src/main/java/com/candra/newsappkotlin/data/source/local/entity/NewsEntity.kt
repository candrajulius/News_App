package com.candra.newsappkotlin.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.candra.newsappkotlin.helper.DataTableNews

@Entity(tableName = "news_table")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = DataTableNews.ID)
    var id: Int,

    @ColumnInfo(name = DataTableNews.AUTHOR)
    var author: String,

    @ColumnInfo(name = DataTableNews.CONTENT)
    var content: String,

    @ColumnInfo(name = DataTableNews.DESCRIPTION)
    var desciption: String,

    @ColumnInfo(name = DataTableNews.PUBLISHED_AT)
    var publishedAt: String,

    @ColumnInfo(name = DataTableNews.TITLE)
    var title: String,

    @ColumnInfo(name = DataTableNews.URL)
    var url: String,

    @ColumnInfo(name = DataTableNews.URL_TO_IMAGE)
    var urlToImage: String,

    @ColumnInfo(name = DataTableNews.IS_FAVORITE)
    var isFavorite: Boolean
)