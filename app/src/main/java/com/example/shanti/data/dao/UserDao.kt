package com.example.shanti.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.shanti.data.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserEntity)

    @Upsert
    suspend fun upsertAll(user: UserEntity)

    @Query("DELETE FROM UserEntity")
    fun clearAll()

    @Delete
    fun delete(user: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE email = :email")
    fun getUserByEmail(email: String): UserEntity?
}