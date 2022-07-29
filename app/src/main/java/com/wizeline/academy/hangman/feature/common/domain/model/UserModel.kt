package com.wizeline.academy.hangman.feature.common.domain.model

data class UserModel(
    val id: Int = NO_ID,
    val name: String
) {
    companion object {
        const val NO_ID = -1
    }
}
