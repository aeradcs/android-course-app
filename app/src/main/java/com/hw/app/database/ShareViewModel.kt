package com.hw.app.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hw.app.api.Api
import com.hw.app.api.ApiAnswerConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ShareViewModel : ViewModel() {
    private val shares: MutableLiveData<List<Share>> = MutableLiveData()

    fun getShares(): LiveData<List<Share>> {
        return shares
    }

    fun loadSharesFromDatabase() {

    }

    fun loadSharesFromApi(symbols: String) {
        viewModelScope.launch (Dispatchers.IO){
            shares.postValue(try {
                val symbolLookup = Api.getSymbolLookup(symbols)
                val tickers = ApiAnswerConverter.parseTickerFromSymbolLookup(symbolLookup)
                val companyProfiles = Api.getCompanyProfilesForTickers(tickers)
                val names = ApiAnswerConverter.parseNamesFromCompanyProfiles(companyProfiles)
                val stockCandles = Api.getOneDayStockCandlesForTickers(tickers)
                val prices = ApiAnswerConverter.parsesPriceFromStockCandles(stockCandles)
                val dayChanges = ApiAnswerConverter.parseDayChangesFromStockCandles(stockCandles)
                ApiAnswerConverter.convertArraysToShares(tickers, names, prices, dayChanges)
            }
            catch (e: Exception){
                val list: MutableList<Share> = mutableListOf()
                list.add(Share("","", 0.0F, 0.0F))
                list
            })

        }

    }
}