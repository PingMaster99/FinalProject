package com.example.newsapp.registered_events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.network.News
import com.example.newsapp.news.NewsListFragmentDirections
import com.example.newsapp.news.NewsListViewModel

class RegisteredViewModel : ViewModel() {

    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>>
        get() = _newsList



    private val _currentNews = MutableLiveData<News>()
    val currentNews: LiveData<News>
        get() = _currentNews

    fun openNewsUrl(news: News){
        NewsListViewModel.registered.remove(news)
        _currentNews.value = news
        _newsList.value = NewsListViewModel.registered.toList()
    }

    init {
        _newsList.value = NewsListViewModel.registered.toList()
    }
}