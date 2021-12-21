package com.hw.app.api

import io.finnhub.api.apis.DefaultApi
import io.finnhub.api.infrastructure.ApiClient
import io.finnhub.api.models.CompanyProfile2
import io.finnhub.api.models.SymbolLookup

object Api {
    suspend fun getSymbolLookup(symbol: String): SymbolLookup {
        ApiClient.apiKey["token"] = "c6idegqad3i8jt9dpdn0"
        val apiClient = DefaultApi()
        val answer = apiClient.symbolSearch(symbol)
        println("getSymbolLookup")
        var a = 10
        println(a)
        return answer
    }

    suspend fun getCompanyProfilesForTickers(tickers: List<String>): List<CompanyProfile2> {
        ApiClient.apiKey["token"] = "c6idegqad3i8jt9dpdn0"
        val apiClient = DefaultApi()
        var result: MutableList<CompanyProfile2> = mutableListOf()
        for (i in 0 until tickers.size){
            result.add(apiClient.companyProfile2(symbol = tickers.get(i), isin = null, cusip = null))
        }
        return result
    }
}