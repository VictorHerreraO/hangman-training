package com.wizeline.academy.hangman.feature.common.data.user.di

import com.wizeline.academy.hangman.feature.common.data.user.datasource.UserLocalDataSourceContract
import com.wizeline.academy.hangman.feature.common.data.user.datasource.impl.UserLocalDataSource
import com.wizeline.academy.hangman.feature.common.data.user.repository.UserRepositoryContract
import com.wizeline.academy.hangman.feature.common.data.user.repository.impl.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserCommonModule {

    @Provides
    fun provideUserLocalDataSourceContract(
        userLocalDataSource: UserLocalDataSource
    ): UserLocalDataSourceContract = userLocalDataSource

    @Provides
    fun provideUserRepositoryContract(
        userRepository: UserRepository
    ): UserRepositoryContract = userRepository

}