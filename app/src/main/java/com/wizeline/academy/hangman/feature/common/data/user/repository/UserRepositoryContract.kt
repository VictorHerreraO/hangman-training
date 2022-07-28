package com.wizeline.academy.hangman.feature.common.data.user.repository

import com.wizeline.academy.hangman.feature.common.domain.model.UserModel

interface UserRepositoryContract {

    suspend fun loginUser(userName: String): Result<UserModel>

}
