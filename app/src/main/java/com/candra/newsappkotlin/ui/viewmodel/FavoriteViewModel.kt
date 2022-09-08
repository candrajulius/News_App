package com.candra.newsappkotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.newsappkotlin.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
): ViewModel()
{
    val getAllFavoriteNews = newsUseCase.getAllFavoriteNews().asLiveData()
}