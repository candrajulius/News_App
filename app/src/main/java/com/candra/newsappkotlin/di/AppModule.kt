package com.candra.newsappkotlin.di

import com.candra.newsappkotlin.domain.usecase.NewsInteract
import com.candra.newsappkotlin.domain.usecase.NewsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule{
    @Binds
    @Singleton
    abstract fun provideNewsUseCase(newsInteract: NewsInteract): NewsUseCase
}