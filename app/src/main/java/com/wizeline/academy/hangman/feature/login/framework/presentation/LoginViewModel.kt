package com.wizeline.academy.hangman.feature.login.framework.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private companion object {
        const val TAG = "LoginViewModel"
    }

    private val _loginState = MutableStateFlow(LoginViewState())
    val loginState = _loginState.asStateFlow()

    fun login() {
        Log.d(TAG, "Do login here!")
        _loginState.update {
            it.copy(loginLoading = true)
        }
    }

    fun onUserNameChanged(userName: String) {
        Log.d(TAG, "user name is $userName")
        _loginState.update {
            it.copy(
                userName = userName,
                loginEnabled = userName.isNotEmpty()
            )
        }
    }

}
