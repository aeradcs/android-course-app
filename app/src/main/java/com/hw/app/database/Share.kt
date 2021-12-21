package com.hw.app.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "share_table")
data class Share (
    @PrimaryKey
    @ColumnInfo(name = "ticker") val ticker: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") var price : Float,
    @ColumnInfo(name = "day_change") var dayChange : Float
    ) : Parcelable