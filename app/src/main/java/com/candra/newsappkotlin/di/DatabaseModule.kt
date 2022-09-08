package com.candra.newsappkotlin.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.candra.newsappkotlin.data.source.local.room.NewsDao
import com.candra.newsappkotlin.data.source.local.room.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,"news.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Provides
    fun providesNewsDao(database: NewsDatabase): NewsDao = database.newsDao()

}