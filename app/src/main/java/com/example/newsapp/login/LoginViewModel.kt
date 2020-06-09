package com.example.newsapp.login

import FirebaseUserLiveData
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.firebase.ui.auth.AuthUI

class LoginViewModel : ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
    fun setUnauthenticated() {
        AuthenticationState.UNAUTHENTICATED
    }

}