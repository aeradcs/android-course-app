package com.hw.app.database

import androidx.lifecycle.LiveData
import com.hw.app.api.Api
import com.hw.app.api.ApiAnswerConverter

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

    fun loadSharesFromApi(symbols: String): List<Share> {
        val symbolLookup = Api.getSymbolLookup(symbols)//to find share by name or ticker
        val tickers = ApiAnswerConverter.parseTickerFromSymbolLookup(symbolLookup)
        val companyProfiles = Api.getCompanyProfilesForTickers(tickers)//to get company name by tickers
        val names = ApiAnswerConverter.parseNamesFromCompanyProfiles(companyProfiles)
        val stockCandles = Api.getOneDayStockCandlesForTickers(tickers)//to get price and dayChange
        val prices = ApiAnswerConverter.parsePricesFromStockCandles(stockCandles)
        val dayChanges = ApiAnswerConverter.parseDayChangesFromStockCandles(stockCandles)
        return ApiAnswerConverter.convertArraysToShares(tickers, names, prices, dayChanges)
    }
}