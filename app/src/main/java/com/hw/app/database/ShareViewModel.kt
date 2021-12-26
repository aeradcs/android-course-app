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
    var status = MutableLiveData<Boolean?>()
    var isLoading = MutableLiveData<Boolean?>()

    init{
        val shareDao = ShareDatabase.getInstance(application).shareDao()
        val cacheShareDao = ShareDatabase.getInstance(application).cacheShareDao()
        repository = ShareRepository(shareDao, cacheShareDao)
        sharesFromDatabase = repository.allSharesFromDatabase
    }

    fun getShares(): LiveData<List<Share>> {
        return shares
    }

    fun setShares(shares: MutableLiveData<List<Share>>) {
        shares.postValue(mutableListOf())
    }

    fun getSharesFromDatabase(): LiveData<List<Share>> {
        return sharesFromDatabase
    }

    fun loadSharesFromApi(symbols: String) {
        viewModelScope.launch (Dispatchers.IO){
            shares.postValue(try {
                isLoading.postValue(true)
                repository.loadSharesFromApi(symbols)
            }
            catch (e: Exception){
                mutableListOf()
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

    fun loadTop15SP500Shares() {
        viewModelScope.launch (Dispatchers.IO){
            shares.postValue(try{
                isLoading.postValue(true)
                repository.loadTop15SP500Shares()
            }catch (e: Exception){
                status.postValue(true)
                mutableListOf()
            })
        }
    }
}
