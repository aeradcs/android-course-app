package com.hw.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Share::class], version = 1, exportSchema = false)
abstract class ShareDatabase : RoomDatabase() {
    abstract fun shareDao(): ShareDao

    companion object{
        @Volatile
        private var INSTANCE: ShareDatabase? = null

        fun getInstance(context: Context): ShareDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShareDatabase::class.java,
                    "db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}