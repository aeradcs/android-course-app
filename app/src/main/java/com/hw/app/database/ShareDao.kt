package com.hw.app.database

import androidx.room.*

@Dao
interface ShareDao {
    @Query("SELECT * FROM share_table")
    fun findAll(): List<Share>

    @Query("SELECT * FROM share_table WHERE ticker LIKE :ticker")
    fun findByTicker(ticker: String): Share

    @Insert
    fun insertShare(share: Share)

    @Delete
    fun deleteShare(share: Share)
}