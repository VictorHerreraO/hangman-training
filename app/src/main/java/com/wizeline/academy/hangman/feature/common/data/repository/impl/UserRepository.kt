package com.wizeline.academy.hangman.feature.common.data.repository.impl

import com.wizeline.academy.hangman.feature.common.data.repository.UserRepositoryContract
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class UserRepository @Inject constructor() : UserRepositoryContract {
    override suspend fun loginUser(userName: String): Result<UserModel> {
        delay(1.seconds)
        return Result.success(
            UserModel(
                id = userName.hashCode(),
                name = userName
            )
        )
    }
}
