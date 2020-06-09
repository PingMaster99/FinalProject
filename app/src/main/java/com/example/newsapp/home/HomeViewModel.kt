package com.example.newsapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.network.AlgoliaApiStatus
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

/**
 * <h1>HomeViewModel</h1>
 *<p>
 * ViewModel for HomeFragment
 *</p>
 *
 * @author Pablo Ruiz (PingMaster99)
 * @version 1.0
 * @since 2020-06-02
 **/
class HomeViewModel : ViewModel() {
    // State of button
    private val _viewNews = MutableLiveData<Boolean>()
    val viewNews: LiveData<Boolean>
        get() = _viewNews

    val user = FirebaseAuth.getInstance().currentUser!!.displayName!!.substringBefore(" ")


    // HourTotal
    private val _totalHours = MutableLiveData<Int>()
    val totalHours: LiveData<Int>
        get() = _totalHours

    // API status
    private val _status = MutableLiveData<AlgoliaApiStatus>()
    val status: LiveData<AlgoliaApiStatus>
        get() = _status

    // Hours
    var hours = 0

    // Button pressed
    fun actionViewNews() {
        _viewNews.value = true
    }

    // Navigation to next fragment
    fun viewNewsComplete() {
        _viewNews.value = false
    }

    fun updateHours(hourIncrement: Int) {
        var previousTotal = _totalHours.value
        if(previousTotal == null) {
            previousTotal = 0
        }
        _totalHours.value = previousTotal + hourIncrement

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
