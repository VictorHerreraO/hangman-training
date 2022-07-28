package com.wizeline.academy.hangman.feature.login.framework.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.academy.hangman.core.util.Event
import com.wizeline.academy.hangman.feature.login.domain.usecase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {
    private companion object {
        const val TAG = "LoginViewModel"
    }

    private val _navigateTo = MutableStateFlow(Event<LoginNavigation?>(null))
    val navigateTo = _navigateTo.asStateFlow()

    private val _loginState = MutableStateFlow(LoginViewState())
    val loginState = _loginState.asStateFlow()

    fun login() = with(_loginState.value) {
        viewModelScope.launch {
            Log.d(TAG, "Do login here!")
            _loginState.update {
                it.copy(loginLoading = true)
            }
            loginUserUseCase(userName).fold(
                onSuccess = {
                    Log.d(TAG, "Logged user $it")
                    _navigateTo.value = Event(
                        LoginNavigation.NavigateHome(it.id)
                    )
                },
                onFailure = {
                    Log.e(TAG, "Unable to login user")
                    _loginState.update {
                        it.copy(loginLoading = false)
                    }
                }
            )
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
