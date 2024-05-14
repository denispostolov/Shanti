package com.example.shanti.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shanti.data.dao.SessionDao
import com.example.shanti.data.entity.SessionEntity

@Database(
    entities = [SessionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SessionDatabase: RoomDatabase() {
    abstract val dao: SessionDao
}