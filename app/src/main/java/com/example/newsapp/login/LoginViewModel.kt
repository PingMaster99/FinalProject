package com.example.newsapp.login

import FirebaseUserLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
/**
 * <h1>LoginViewModel</h1>
 *<p>
 * ViewModel used in LoginFragment
 *</p>
 *
 * @author Pablo Ruiz (PingMaster99), Javier Salazar (zombiewafle), Joonho Kim (jkmolina)
 * @version 1.0
 * @since 2020-06-08
 **/
class LoginViewModel : ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }
    var authenticated = false
    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}