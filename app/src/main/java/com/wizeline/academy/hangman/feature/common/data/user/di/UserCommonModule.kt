package com.wizeline.academy.hangman.feature.common.data.user.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.soyvictorherrera.bdates.core.arch.Mapper
import com.wizeline.academy.hangman.feature.common.data.user.datasource.UserLocalDataSourceContract
import com.wizeline.academy.hangman.feature.common.data.user.datasource.impl.UserLocalDataSource
import com.wizeline.academy.hangman.feature.common.data.user.datasource.impl.UserModelToEntityMapper
import com.wizeline.academy.hangman.feature.common.data.user.repository.UserRepositoryContract
import com.wizeline.academy.hangman.feature.common.data.user.repository.impl.UserRepository
import com.wizeline.academy.hangman.feature.common.data.user.room.UserDao
import com.wizeline.academy.hangman.feature.common.data.user.room.UserDataBase
import com.wizeline.academy.hangman.feature.common.data.user.room.UserEntity
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class UserCommonModule {

    private companion object {
        const val USER_DATA_BASE = "user_data_base"
    }

    @Provides
    @Named(USER_DATA_BASE)
    fun provideUserDataBase(
        application: Application
    ): UserDataBase = Room.databaseBuilder(
        application,
        UserDataBase::class.java,
        "user.db"
    ).build()

    @Provides
    fun provideUserDao(
        @Named(USER_DATA_BASE) userDataBase: UserDataBase
    ): UserDao = userDataBase.userDao()

    @Provides
    fun provideUserModelToEntityMapper(
        userModelToEntityMapper: UserModelToEntityMapper
    ): Mapper<UserModel, UserEntity> = userModelToEntityMapper

    @Provides
    fun provideUserLocalDataSourceContract(
        userLocalDataSource: UserLocalDataSource
    ): UserLocalDataSourceContract = userLocalDataSource

    @Provides
    fun provideUserRepositoryContract(
        userRepository: UserRepository
    ): UserRepositoryContract = userRepository

}