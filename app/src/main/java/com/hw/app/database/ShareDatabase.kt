package com.hw.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Share::class], version = 1)
abstract class ShareDatabase : RoomDatabase() {
    abstract fun shareDao(): ShareDao

    companion object {
        fun getInstance(context: Context): ShareDatabase {
            return Room.databaseBuilder(context, ShareDatabase::class.java, "db")
                .build()
        }
    }
}