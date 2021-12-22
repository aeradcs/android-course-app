package com.hw.app.database

import androidx.lifecycle.LiveData
import com.hw.app.api.Api
import com.hw.app.api.ApiAnswerConverter
import com.hw.app.database.cache.CacheShare
import com.hw.app.database.cache.CacheShareDao
import com.hw.app.database.data.Data

class ShareRepository(private val shareDao: ShareDao, private val cacheShareDao: CacheShareDao) {
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

    suspend fun loadTop15SP500Shares(): List<Share> {
        //if last rest api call was made more than one day ago then make new request else fetch data from cache
        val currentTime = (System.currentTimeMillis() / 1000L)
        if(cacheShareDao.countCachedRows() == 0 || ((currentTime - cacheShareDao.getTime()) > 86400L)){
            val tickers = Data.getTop15SP500Tickers()
            val companyProfiles = Api.getCompanyProfilesForTickers(tickers)//to get company name by tickers
            val names = ApiAnswerConverter.parseNamesFromCompanyProfiles(companyProfiles)
            val stockCandles = Api.getOneDayStockCandlesForTickers(tickers)//to get price and dayChange
            val prices = ApiAnswerConverter.parsePricesFromStockCandles(stockCandles)
            val dayChanges = ApiAnswerConverter.parseDayChangesFromStockCandles(stockCandles)

            //save to cache
            saveTop15SP500SharesToCache(currentTime, tickers, names, prices, dayChanges)

            return ApiAnswerConverter.convertArraysToShares(tickers, names, prices, dayChanges)
        }else{
            val shares : MutableList<Share> = mutableListOf()
            val cachedShares = cacheShareDao.findAll()
            var cachedShare: CacheShare
            for(i in 0 until cacheShareDao.countCachedRows()){
                cachedShare = cachedShares.get(i)
                shares.add(Share(cachedShare.ticker, cachedShare.name, cachedShare.price, cachedShare.dayChange))
            }
            return shares
        }
    }

    private suspend fun saveTop15SP500SharesToCache(currentTime: Long, tickers: List<String>, names: List<String>,
                                                    prices: List<Float?>, dayChanges: List<Float?>) {
        for(i in tickers.indices){
            cacheShareDao.insertShare(CacheShare(tickers[i], names[i], prices[i]!!, dayChanges[i]!!, currentTime))
        }
    }
}