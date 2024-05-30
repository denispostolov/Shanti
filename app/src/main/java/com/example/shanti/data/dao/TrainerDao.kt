package com.example.shanti.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.shanti.data.entity.TrainerEntity
import com.example.shanti.domain.model.PractiseType
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

    @Query("SELECT * FROM TrainerEntity WHERE practiseType = :practiseType")
    fun getTrainersByPractiseType(practiseType: PractiseType): Flow<List<TrainerEntity>>
}