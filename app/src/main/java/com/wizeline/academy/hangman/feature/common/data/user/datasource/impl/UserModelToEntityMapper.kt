package com.wizeline.academy.hangman.feature.common.data.user.datasource.impl

import com.soyvictorherrera.bdates.core.arch.Mapper
import com.wizeline.academy.hangman.feature.common.data.user.room.UserEntity
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel
import javax.inject.Inject

class UserModelToEntityMapper @Inject constructor() : Mapper<UserModel, UserEntity>() {
    override fun map(value: UserModel): UserEntity = with(value) {
        return UserEntity(
            id = id,
            name = name
        )
    }
}