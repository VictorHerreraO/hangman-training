package com.wizeline.academy.hangman.feature.common.data.user.datasource.impl

import com.wizeline.academy.hangman.core.arch.IoDispatcher
import com.wizeline.academy.hangman.feature.common.data.user.datasource.UserLocalDataSourceContract
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class UserLocalDataSource @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UserLocalDataSourceContract {
    override suspend fun saveUser(userModel: UserModel): Result<UserModel> {
        return withContext(dispatcher) {
            delay(1.seconds)
            Result.success(
                userModel.copy(id = userModel.name.hashCode())
            )
        }
    }
}
