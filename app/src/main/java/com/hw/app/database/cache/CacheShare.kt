package com.hw.app.database.cache

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cache_table")
data class CacheShare (
    @PrimaryKey
    @ColumnInfo(name = "ticker") val ticker: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") var price : Float,
    @ColumnInfo(name = "day_change") var dayChange : Float,
    @ColumnInfo(name = "time")val time: Long,
    @ColumnInfo(name = "logo") val logo: String
    ):Parcelable