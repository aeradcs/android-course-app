package com.hw.app.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "share_table")
data class Share (
    @PrimaryKey
    @ColumnInfo(name = "ticker") val ticker: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") var price : Double,
    @ColumnInfo(name = "day_change") var dayChange : Double
    )