package com.wizeline.academy.hangman.feature.common.data.user.repository.impl

import com.wizeline.academy.hangman.feature.common.data.user.datasource.UserLocalDataSourceContract
import com.wizeline.academy.hangman.feature.common.data.user.repository.UserRepositoryContract
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSourceContract
) : UserRepositoryContract {
    override suspend fun loginUser(userName: String): Result<UserModel> {
        return userLocalDataSource.saveUser(
            userModel = UserModel(name = userName)
        )
    }
}
