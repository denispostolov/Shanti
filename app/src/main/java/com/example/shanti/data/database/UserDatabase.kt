package com.example.shanti.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shanti.data.dao.UserDao
import com.example.shanti.data.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase: RoomDatabase() {
    abstract val dao: UserDao
}