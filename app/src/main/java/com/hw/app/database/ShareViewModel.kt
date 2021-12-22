package com.hw.app.database

import android.app.Application
import androidx.core.widget.ListViewAutoScrollHelper
import androidx.lifecycle.*
import com.hw.app.api.Api
import com.hw.app.api.ApiAnswerConverter
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

    fun loadSharesFromDatabase() {

    }

    fun loadSharesFromApi(symbols: String) {
        viewModelScope.launch (Dispatchers.IO){
            shares.postValue(try {
                val symbolLookup = Api.getSymbolLookup(symbols)//to find share by name or ticker
                val tickers = ApiAnswerConverter.parseTickerFromSymbolLookup(symbolLookup)
                val companyProfiles = Api.getCompanyProfilesForTickers(tickers)//to get company name of share
                val names = ApiAnswerConverter.parseNamesFromCompanyProfiles(companyProfiles)
                val stockCandles = Api.getOneDayStockCandlesForTickers(tickers)//to get price and dayChange
                val prices = ApiAnswerConverter.parsePricesFromStockCandles(stockCandles)
                val dayChanges = ApiAnswerConverter.parseDayChangesFromStockCandles(stockCandles)
                ApiAnswerConverter.convertArraysToShares(tickers, names, prices, dayChanges)
            }
            catch (e: Exception){
                val list: MutableList<Share> = mutableListOf()
                list
            })

        }

    }

    fun deleteShare(share: Share) {
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteShare(share)
       }
    }

    fun insertShare(share: Share) {
        viewModelScope.launch (Dispatchers.IO){
            repository.insertShare(share)
        }
    }
}
