package com.hw.app.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ShareRepository

    init{
        val shareDao = ShareDatabase.getInstance(application).shareDao()
        val cacheShareDao = ShareDatabase.getInstance(application).cacheShareDao()
        repository = ShareRepository(shareDao, cacheShareDao)
    }

    fun deleteShare(share: Share) {
        viewModelScope.launch {
            repository.deleteShare(share)
        }
    }

    fun insertShare(share: Share) {
        viewModelScope.launch {
            repository.insertShare(share)
        }
    }
}