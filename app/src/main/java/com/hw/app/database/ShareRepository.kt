package com.hw.app.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ShareRepository(private val shareDao: ShareDao) {
    val allSharesFromDatabase: LiveData<List<Share>> = shareDao.findAll()

    suspend fun insertShare(share: Share){
        if(shareDao.containsShare(share.ticker) == 0){
            shareDao.insertShare(share)
        }
    }

    suspend fun deleteShare(share: Share){
        if(shareDao.containsShare(share.ticker) == 1){
            shareDao.deleteShare(share)
        }
    }
}