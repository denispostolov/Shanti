package com.example.shanti.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.shanti.data.entity.TrainerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainerDao {

    @Insert
    fun insert(trainer: TrainerEntity)

    @Delete
    fun delete(trainer: TrainerEntity)

    @Query("DELETE FROM TrainerEntity")
    fun clearAll()

    @Query("SELECT * FROM TrainerEntity")
    fun getAll(): Flow<List<TrainerEntity>>

    @Query("SELECT * FROM TrainerEntity WHERE email = :email")
    fun getTrainerByEmail(email: String): TrainerEntity?
}