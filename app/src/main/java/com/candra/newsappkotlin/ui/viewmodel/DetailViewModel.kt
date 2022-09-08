package com.candra.newsappkotlin.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.domain.usecase.NewsUseCase
import com.candra.newsappkotlin.helper.Constant
import com.candra.newsappkotlin.helper.Convert.formatContentEventSnackBarText
import com.candra.newsappkotlin.helper.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
): ViewModel()
{

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite get() = _isFavorite

    private var _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText get() = _snackBarText


    fun insertNewsToFavorite(news: NewsModel,title: String,context: Context) = viewModelScope.launch {
        Log.d(Constant.TAG, "insertNewsToFavorite: $news")
        newsUseCase.insertDataToFavorite(news)
        _snackBarText.value = Event(context.formatContentEventSnackBarText(title))
    }

    fun removeNewsFromFavorite(news: NewsModel,title: String,context: Context) = viewModelScope.launch {
        Log.d(Constant.TAG, "removeNewsFromFavorite: $news")
        newsUseCase.deleteFromFavoriteNews(news)
        _snackBarText.value = Event(context.formatContentEventSnackBarText(title))
    }

    fun isFavoriteFromFavorite(title: String) = viewModelScope.launch {
        newsUseCase.isFavoriteNews(title).collect{
            _isFavorite.value = it
        }
    }
}