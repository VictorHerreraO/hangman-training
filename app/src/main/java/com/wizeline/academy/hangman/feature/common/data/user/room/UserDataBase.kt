package com.wizeline.academy.hangman.feature.common.data.user.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
