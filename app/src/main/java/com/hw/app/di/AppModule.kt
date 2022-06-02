//package com.hw.app.di
//
//import android.content.Context
//import com.hw.app.database.ShareDao
//import com.hw.app.database.ShareDatabase
//import com.hw.app.database.cache.CacheShareDao
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//    @Singleton
//    @Provides
//    fun getDatabase(context: Context): ShareDatabase{
//        return ShareDatabase.getInstance(context)
//    }
//
//    @Singleton
//    @Provides
//    fun getShareDao(database: ShareDatabase): ShareDao{
//        return database.shareDao()
//    }
//
//    @Singleton
//    @Provides
//    fun getCacheDao(database: ShareDatabase): CacheShareDao {
//        return database.cacheShareDao()
//    }
//}