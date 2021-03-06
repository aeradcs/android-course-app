package com.hw.app.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShareDao {
    @Query("SELECT * FROM share_table")
    fun findAll(): LiveData<List<Share>>

    @Query("SELECT count(*) FROM share_table WHERE ticker LIKE :ticker")
    suspend fun countRows(ticker: String) : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertShare(share: Share)

    @Delete
    suspend fun deleteShare(share: Share)
}