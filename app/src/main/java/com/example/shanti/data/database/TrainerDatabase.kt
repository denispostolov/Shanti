package com.example.shanti.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shanti.data.dao.TrainerDao
import com.example.shanti.data.entity.TrainerEntity

@Database(
    entities = [TrainerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TrainerDatabase: RoomDatabase() {
    abstract val dao: TrainerDao
}