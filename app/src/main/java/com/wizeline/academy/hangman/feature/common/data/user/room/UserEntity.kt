package com.wizeline.academy.hangman.feature.common.data.user.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String
)
