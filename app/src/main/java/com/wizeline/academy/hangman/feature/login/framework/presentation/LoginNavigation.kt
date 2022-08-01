package com.wizeline.academy.hangman.feature.login.framework.presentation

sealed class LoginNavigation {
    data class NavigateHome(val userId: Int) : LoginNavigation()
}
