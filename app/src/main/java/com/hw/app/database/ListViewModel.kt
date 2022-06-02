package com.hw.app.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val shares: MutableLiveData<List<Share>> = MutableLiveData()
    private val repository: ShareRepository
    var status = MutableLiveData<Boolean?>()
    var isLoading = MutableLiveData<Boolean?>()

    init{
        val shareDao = ShareDatabase.getInstance(application).shareDao()
        val cacheShareDao = ShareDatabase.getInstance(application).cacheShareDao()
        repository = ShareRepository(shareDao, cacheShareDao)
    }

    fun getShares(): LiveData<List<Share>> {
        return shares
    }

    fun setShares(shares: MutableLiveData<List<Share>>) {
        shares.postValue(mutableListOf())
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
