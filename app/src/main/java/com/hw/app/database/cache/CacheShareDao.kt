package com.hw.app.database.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CacheShareDao {
    @Query("SELECT * FROM cache_table")
    suspend fun findAll(): List<CacheShare>

    @Query("SELECT time FROM cache_table LIMIT 1")
    suspend fun getTime(): Long

    @Query("SELECT count(*) FROM cache_table")
    suspend fun countCachedRows() : Int

    @Insert
    suspend fun insertShare(cacheShare: CacheShare)

    @Delete
    suspend fun deleteShare(cacheShare: CacheShare)
}