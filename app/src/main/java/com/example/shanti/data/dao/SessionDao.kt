package com.example.shanti.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.shanti.data.entity.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sessions: List<SessionEntity>)

    @Upsert
    suspend fun insert(session: SessionEntity)

    @Query("SELECT * FROM SessionEntity ORDER BY dateTime DESC, time ASC")
    fun getAllSessions(): Flow<List<SessionEntity>>

    @Query("DELETE FROM SessionEntity")
    fun clearAll()

    @Delete
    suspend fun deleteSession(session: SessionEntity)

}