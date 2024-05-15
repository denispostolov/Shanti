package com.example.shanti.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shanti.data.entity.SessionEntity

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<SessionEntity>)

    @Query("SELECT * FROM SessionEntity")
    fun pagingSource(): PagingSource<Int, SessionEntity>

    @Query("DELETE FROM SessionEntity")
    fun clearAll()

}