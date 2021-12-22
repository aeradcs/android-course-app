package com.hw.app.api

import com.hw.app.database.Share
import io.finnhub.api.models.CompanyProfile2
import io.finnhub.api.models.StockCandles
import io.finnhub.api.models.SymbolLookup
import java.lang.Exception

object ApiAnswerConverter {
    fun parseTickerFromSymbolLookup(answer: SymbolLookup): List<String>{
        val ticker: MutableList<String> = mutableListOf()
        for (i in 0 until answer.result!!.size){
            ticker.add(answer.result!![i.toInt()].symbol.toString())
        }
        return ticker
    }

    fun parseNamesFromCompanyProfiles(companyProfiles: List<CompanyProfile2>): List<String>{
        val names: MutableList<String> = mutableListOf()
        for (i in 0 until companyProfiles.size){
            names.add(companyProfiles.get(i).name.toString())
        }
        return names
    }

    fun parsePricesFromStockCandles(stockCandles: MutableList<StockCandles?>): MutableList<Float?> {
        val prices: MutableList<Float?> = mutableListOf()
        for (i in 0 until stockCandles.size) {
            try {
                prices.add(stockCandles.get(i)!!.c!!.get(1))
            }catch (e: Exception){
                prices.add(null)
            }
        }
        return prices
    }

    fun parseDayChangesFromStockCandles(stockCandles: MutableList<StockCandles?>): MutableList<Float?> {
        val dayChanges: MutableList<Float?> = mutableListOf()
        for (i in 0 until stockCandles.size) {
            try {
                //get dayChange in percents
                dayChanges.add((stockCandles.get(i)!!.c!!.get(1) * 100 / stockCandles.get(i)!!.c!!.get(0)) - 100F)
            }catch (e: Exception){
                dayChanges.add(null)
            }
        }
        return dayChanges
    }

    fun convertArraysToShares(tickers: List<String>, names: List<String>, prices: List<Float?>, dayChanges: List<Float?>): List<Share> {
        val shares: MutableList<Share> = mutableListOf()
        for(i in 0 until tickers.size){
            shares.add(Share(tickers.get(i), names.get(i), prices.get(i)!!, dayChanges.get(i)!!))
        }
        return shares
    }
}