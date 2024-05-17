package com.example.shanti.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shanti.data.entity.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<SessionEntity>)
    @Query("SELECT * FROM SessionEntity")
    fun getAllSessions(): Flow<List<SessionEntity>>

    @Query("DELETE FROM SessionEntity")
    fun clearAll()

}