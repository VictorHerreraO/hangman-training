package com.wizeline.academy.hangman.feature.common.data.user.datasource

import com.wizeline.academy.hangman.feature.common.domain.model.UserModel

interface UserLocalDataSourceContract {

    suspend fun saveUser(userModel: UserModel): Result<UserModel>

}
