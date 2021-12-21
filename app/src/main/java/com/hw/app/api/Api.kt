package com.hw.app.api

import io.finnhub.api.apis.DefaultApi
import io.finnhub.api.infrastructure.ApiClient
import io.finnhub.api.models.CompanyProfile2
import io.finnhub.api.models.StockCandles
import io.finnhub.api.models.SymbolLookup
import io.finnhub.api.models.SymbolLookupInfo
import java.lang.Exception
import java.time.LocalDate
import java.util.Calendar.*

object Api {
    val apiClient : DefaultApi

    init{
        ApiClient.apiKey["token"] = "c6idegqad3i8jt9dpdn0"
        apiClient = DefaultApi()
    }

    fun getSymbolLookup(symbol: String): SymbolLookup {
        val answer = apiClient.symbolSearch(symbol)
        if(answer.count!! < 2){
            return answer
        }
        else{
            val newList: MutableList<SymbolLookupInfo> = mutableListOf()
            for(i in 0 until 1){
                newList.add(answer.result!!.get(i))
            }
            return SymbolLookup(newList, 1)
        }
    }

    fun getCompanyProfilesForTickers(tickers: List<String>): List<CompanyProfile2> {
        val answer: MutableList<CompanyProfile2> = mutableListOf()
        for (i in 0 until tickers.size){
            answer.add(apiClient.companyProfile2(symbol = tickers.get(i), isin = null, cusip = null))
        }
        return answer
    }

    private fun getStockCandlesForTicker(ticker: String, resolution: String, from: Long, to: Long): StockCandles {
        val answer = apiClient.stockCandles(ticker, resolution, from, to)
        return answer
    }

    fun getOneDayStockCandlesForTickers(tickers: List<String>): MutableList<StockCandles?> {
        val answer: MutableList<StockCandles?> = mutableListOf()
        val unixTimeTo = System.currentTimeMillis() / 1000L
        val unixTimeFrom : Long
        when (LocalDate.now().getDayOfWeek().value + 1) {
            MONDAY -> unixTimeFrom = unixTimeTo - 86400 * 3
            TUESDAY -> unixTimeFrom = unixTimeTo - 86400 * 4
            else -> unixTimeFrom = unixTimeTo - 86400 * 2
        }
        println(unixTimeTo)
        for(i in 0 until tickers.size){
            try {
                answer.add(getStockCandlesForTicker(tickers.get(i), "D", unixTimeFrom, unixTimeTo))
            }catch (e: Exception){
                answer.add(null)
            }
        }
        return answer
    }
}