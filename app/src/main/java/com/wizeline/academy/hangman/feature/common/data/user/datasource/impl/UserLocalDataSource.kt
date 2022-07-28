package com.wizeline.academy.hangman.feature.common.data.user.datasource.impl

import com.soyvictorherrera.bdates.core.arch.Mapper
import com.wizeline.academy.hangman.core.arch.IoDispatcher
import com.wizeline.academy.hangman.feature.common.data.user.datasource.UserLocalDataSourceContract
import com.wizeline.academy.hangman.feature.common.data.user.room.UserDao
import com.wizeline.academy.hangman.feature.common.data.user.room.UserEntity
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class UserLocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val userModelToEntityMapper: Mapper<UserModel, UserEntity>,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UserLocalDataSourceContract {

    override suspend fun saveUser(userModel: UserModel): Result<UserModel> =
        withContext(dispatcher) {
            runCatching {
                val toInsert = userModel.copy(id = userModel.name.hashCode())
                userDao.insert(
                    userModelToEntityMapper.map(toInsert)
                )
                delay(1.seconds)
                return@runCatching toInsert
            }
        }

}
