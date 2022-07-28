package com.wizeline.academy.hangman.feature.login.di

import com.soyvictorherrera.bdates.core.arch.UseCase
import com.wizeline.academy.hangman.feature.common.data.repository.UserRepositoryContract
import com.wizeline.academy.hangman.feature.common.data.repository.impl.UserRepository
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel
import com.wizeline.academy.hangman.feature.login.domain.usecase.LoginUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    fun provideUserRepositoryContract(
        userRepository: UserRepository
    ): UserRepositoryContract = userRepository

    @Provides
    fun provideLoginUserUseCase(
        loginUserUseCase: LoginUserUseCase
    ): UseCase<String, Result<UserModel>> = loginUserUseCase

}
