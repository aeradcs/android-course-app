package com.hw.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hw.app.database.cache.CacheShare
import com.hw.app.database.cache.CacheShareDao

@Database(entities = [Share::class, CacheShare::class], version = 2, exportSchema = false)
abstract class ShareDatabase : RoomDatabase() {
    abstract fun shareDao(): ShareDao
    abstract fun cacheShareDao(): CacheShareDao

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
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}