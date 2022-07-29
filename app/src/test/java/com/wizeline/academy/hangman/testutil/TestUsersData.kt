package com.wizeline.academy.hangman.testutil

import com.wizeline.academy.hangman.feature.common.data.user.room.UserEntity
import com.wizeline.academy.hangman.feature.common.domain.model.UserModel

object TestUsersData {
    val user_model_1 = UserModel(
        id = "John".hashCode(),
        name = "John"
    )
    val user_model_no_id = UserModel(
        name = "Margaret"
    )
    val user_entity_1 = UserEntity(
        id = "John".hashCode(),
        name = "John"
    )
}