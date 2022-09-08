package com.candra.newsappkotlin.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.candra.newsappkotlin.data.source.remote.States
import com.candra.newsappkotlin.domain.model.NewsModel
import com.candra.newsappkotlin.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
): ViewModel(){

    private lateinit var _topHeadlineNews: LiveData<PagingData<NewsModel>>
    val topHeadlineNews get() = _topHeadlineNews

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading get() = _isLoading

    fun getTopHeadlinesNewsByCountryAndCategory(country: String,category: String) =
        viewModelScope.launch {
            _topHeadlineNews = newsUseCase.getTopHeadlinesByCountryAndCategory(country, category).cachedIn(viewModelScope)
        }

    fun searchValueList(query: String) = newsUseCase.searchEverythingFromNews(query).asLiveData()


    fun setLoading(isLoading: Boolean){
        _isLoading.value = isLoading
    }
}