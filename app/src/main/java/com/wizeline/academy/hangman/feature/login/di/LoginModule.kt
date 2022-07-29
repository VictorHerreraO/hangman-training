package com.wizeline.academy.hangman.feature.login.di

import com.soyvictorherrera.bdates.core.arch.UseCase
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel
import com.wizeline.academy.hangman.feature.login.domain.usecase.LoginUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class LoginModule {

    @[Provides ViewModelScoped]
    fun provideLoginUserUseCase(
        loginUserUseCase: LoginUserUseCase
    ): UseCase<String, Result<UserModel>> = loginUserUseCase

}
