package com.wizeline.academy.hangman.feature.login.domain.usecase

import com.soyvictorherrera.bdates.core.arch.UseCase
import com.wizeline.academy.hangman.feature.common.data.user.repository.UserRepositoryContract
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryContract
) : UseCase<String, Result<UserModel>>() {

    override suspend fun invoke(userName: String): Result<UserModel> {
        return if (userName.isEmpty()) Result.failure(IllegalArgumentException("`name` is required"))
        else userRepository.loginUser(userName)
    }

}
