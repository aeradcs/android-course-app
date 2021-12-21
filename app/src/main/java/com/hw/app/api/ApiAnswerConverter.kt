package com.hw.app.api

import com.hw.app.database.Share
import io.finnhub.api.models.CompanyProfile2
import io.finnhub.api.models.SymbolLookup

object ApiAnswerConverter {
    suspend fun parseTickerFromSymbolLookup(answer: SymbolLookup): List<String>{
        val ticker: MutableList<String> = mutableListOf()
        for (i in 0 until answer.result!!.size){
            ticker.add(answer.result!![i.toInt()].symbol.toString())
        }
        return ticker
    }

    suspend fun parseNamesFromCompanyProfiles(companyProfiles: List<CompanyProfile2>): List<String>{
        val names: MutableList<String> = mutableListOf()
        for (i in 0 until companyProfiles.size){
            names.add(companyProfiles.get(i).name.toString())
        }
        return names
    }

    suspend fun convertArraysToShares(tickers: List<String>, names: List<String>): List<Share> {
        val shares: MutableList<Share> = mutableListOf()
        for(i in 0 until tickers.size){
            shares.add(Share(tickers.get(i), names.get(i), 0.0,0.0))
        }
        return shares
    }


}