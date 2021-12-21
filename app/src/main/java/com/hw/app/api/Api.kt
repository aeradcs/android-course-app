package com.hw.app.api

import io.finnhub.api.apis.DefaultApi
import io.finnhub.api.infrastructure.ApiClient
import io.finnhub.api.models.CompanyProfile2
import io.finnhub.api.models.SymbolLookup
import io.finnhub.api.models.SymbolLookupInfo

object Api {
    fun getSymbolLookup(symbol: String): SymbolLookup {
        ApiClient.apiKey["token"] = "c6idegqad3i8jt9dpdn0"
        val apiClient = DefaultApi()
        val answer = apiClient.symbolSearch(symbol)
        if(answer.count!! < 15){
            return answer
        }
        else{
            val newList: MutableList<SymbolLookupInfo> = mutableListOf()
            for(i in 0 until 15){
                newList.add(answer.result!!.get(i))
            }
            return SymbolLookup(newList, 15)

        }
    }

    fun getCompanyProfilesForTickers(tickers: List<String>): List<CompanyProfile2> {
        ApiClient.apiKey["token"] = "c6idegqad3i8jt9dpdn0"
        val apiClient = DefaultApi()
        var result: MutableList<CompanyProfile2> = mutableListOf()
        for (i in 0 until tickers.size){
            result.add(apiClient.companyProfile2(symbol = tickers.get(i), isin = null, cusip = null))
        }
        return result
    }
}