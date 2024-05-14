package com.example.shanti.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.shanti.data.entity.TrainerEntity

@Dao
interface TrainerDao {

    @Insert
    fun insert(trainer: TrainerEntity)

    @Delete
    fun delete(trainer: TrainerEntity)

    @Query("DELETE FROM trainerentity")
    fun clearAll()

    @Query("SELECT * FROM trainerentity")
    fun getAll(): List<TrainerEntity>

    @Query("SELECT * FROM trainerentity WHERE email = :email")
    fun getTrainerByEmail(email: String): TrainerEntity?
}