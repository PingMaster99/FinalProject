package com.example.newsapp.news


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.network.*
import kotlinx.coroutines.*
/**
 * <h1>NewsListViewModel</h1>
 *<p>
 * ViewModel for NewsListFragment
 *</p>
 *
 * @author Pablo Ruiz (PingMaster99)
 * @version 1.0
 * @since 2020-06-02
 **/
class NewsListViewModel() : ViewModel() {
    companion object {
        var registered: MutableList<News> = mutableListOf()
    }

    private lateinit var newsDeferred: Deferred<Website>
    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>>
        get() = _newsList


    private val _currentNews = MutableLiveData<News>()
    val currentNews: LiveData<News>
        get() = _currentNews

    private val _status = MutableLiveData<AlgoliaApiStatus>()
    val status: LiveData<AlgoliaApiStatus>
        get() = _status


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        startStatus()
        getEvents()
    }

    var can_assist = false

    fun startStatus(){
        _status.value = AlgoliaApiStatus.START
    }

    fun openNewsUrl(news: News){
        _currentNews.value = news
        registered.add(news)
    }

    fun updateEvents(month: String){
        coroutineScope.launch {
            newsDeferred = AlgoliaApi.retrofitService.getCurrentEvents(month)
            _status.value = AlgoliaApiStatus.LOADING
            val news = newsDeferred.await().response.holidays
            _status.value = AlgoliaApiStatus.DONE
            _newsList.value = news
            try {
                _status.value = AlgoliaApiStatus.LOADING
                val news = newsDeferred.await().response.holidays
                _status.value = AlgoliaApiStatus.DONE
                _newsList.value = news
            } catch (e: Exception){
                _status.value = AlgoliaApiStatus.ERROR
                _newsList.value = emptyList()
            }
        }
    }

    fun getEvents(){
        coroutineScope.launch {
            newsDeferred = AlgoliaApi.retrofitService.getNewsAsync()
            try {
                _status.value = AlgoliaApiStatus.LOADING
                val news = newsDeferred.await().response.holidays
                _status.value = AlgoliaApiStatus.DONE
                _newsList.value = news
            } catch (e: Exception){
                _status.value = AlgoliaApiStatus.ERROR
                _newsList.value = emptyList()
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
