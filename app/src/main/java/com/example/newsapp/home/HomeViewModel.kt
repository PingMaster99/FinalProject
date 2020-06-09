package com.example.newsapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.network.AlgoliaApiStatus
import com.google.firebase.auth.FirebaseAuth

/**
 * <h1>HomeViewModel</h1>
 *<p>
 * ViewModel for HomeFragment
 *</p>
 *
 * @author Pablo Ruiz (PingMaster99)
 * @version 2.0
 * @since 2020-06-02
 **/
class HomeViewModel : ViewModel() {
    // State of button
    private val _viewNews = MutableLiveData<Boolean>()
    val viewNews: LiveData<Boolean>
        get() = _viewNews

    val user = FirebaseAuth.getInstance().currentUser!!.displayName!!.substringBefore(" ")



    // API status
    private val _status = MutableLiveData<AlgoliaApiStatus>()
    val status: LiveData<AlgoliaApiStatus>
        get() = _status

    companion object {
        var hours = 0
    }

    // Button pressed
    fun actionViewNews() {
        _viewNews.value = true
    }

    // Navigation to next fragment
    fun viewNewsComplete() {
        _viewNews.value = false
    }

    fun updateHours(hourIncrement: Int) {
        hours += hourIncrement
    }

    fun getHours() : Int {
        return hours
    }
    // Constructor
    init {
        startStatus()
    }

    // Starts API
    fun startStatus(){
        _status.value = AlgoliaApiStatus.START
    }

}
