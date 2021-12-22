package com.hw.app.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ShareViewModel(application: Application) : AndroidViewModel(application) {
    private val shares: MutableLiveData<List<Share>> = MutableLiveData()
    private val repository: ShareRepository
    private val sharesFromDatabase: LiveData<List<Share>>

    init{
        val shareDao = ShareDatabase.getInstance(application).shareDao()
        repository = ShareRepository(shareDao)
        sharesFromDatabase = repository.allSharesFromDatabase
    }

    fun getShares(): LiveData<List<Share>> {
        return shares
    }

    fun getSharesFromDatabase(): LiveData<List<Share>> {
        return sharesFromDatabase
    }

    fun loadSharesFromApi(symbols: String) {
        viewModelScope.launch (Dispatchers.IO){
            shares.postValue(try {
                repository.loadSharesFromApi(symbols)
            }
            catch (e: Exception){
                val list: MutableList<Share> = mutableListOf()
                list
            })
        }
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
