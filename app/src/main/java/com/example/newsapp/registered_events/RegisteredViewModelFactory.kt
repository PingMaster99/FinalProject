package com.example.newsapp.registered_events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * <h1>NewsListViewModelFactory</h1>
 *<p>
 * Factory for NewsListViewModel
 *</p>
 *
 * @author Pablo Ruiz (PingMaster99)
 * @version 1.0
 * @since 2020-06-02
 **/
class RegisteredViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisteredViewModel::class.java)) {
            return RegisteredViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}