package com.wizeline.academy.hangman.feature.login.framework.presentation

data class LoginViewState(
    val userName: String = "",
    val loginEnabled: Boolean = false,
    val loginLoading: Boolean = false
)
