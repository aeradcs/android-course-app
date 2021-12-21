package com.hw.app.database

import androidx.lifecycle.*
import com.hw.app.api.Api
import com.hw.app.api.ApiAnswerConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ShareViewModel() : ViewModel() {
    private val shares: MutableLiveData<List<Share>> = MutableLiveData()

    fun getShares(): LiveData<List<Share>> {
        return shares
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
//                list.add(Share("","", 0.0F, 0.0F))
                list
            })

        }

    }

    fun deleteShare(share: Share) {
        viewModelScope.launch(Dispatchers.IO){

       }
    }

    fun insertShare(share: Share) {
        viewModelScope.launch(Dispatchers.IO){

        }
    }
}

//class WordViewModelFactory(private val repository: ShareRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ShareViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ShareViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}