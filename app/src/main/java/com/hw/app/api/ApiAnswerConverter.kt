package com.hw.app.api

import com.hw.app.database.Share
import io.finnhub.api.models.CompanyProfile2
import io.finnhub.api.models.StockCandles
import io.finnhub.api.models.SymbolLookup
import java.lang.Exception

object ApiAnswerConverter {
    fun parseTickerFromSymbolLookup(answer: SymbolLookup): List<String>{
        val ticker: MutableList<String> = mutableListOf()
        for (i in answer.result!!.indices){
            ticker.add(answer.result!![i].symbol.toString())
        }
        return ticker
    }

    fun parseNamesFromCompanyProfiles(companyProfiles: List<CompanyProfile2>): List<String>{
        val names: MutableList<String> = mutableListOf()
        for (i in companyProfiles.indices){
            names.add(companyProfiles[i].name.toString())
        }
        return names
    }

    fun parseLogosFromCompanyProfiles(companyProfiles: List<CompanyProfile2>): List<String>{
        val logos: MutableList<String> = mutableListOf()
        for (i in companyProfiles.indices){
            logos.add(companyProfiles[i].logo.toString())
        }
        return logos
    }

    fun parsePricesFromStockCandles(stockCandles: MutableList<StockCandles>): MutableList<Float> {
        val prices: MutableList<Float> = mutableListOf()
        for (i in 0 until stockCandles.size) {
            try{
                prices.add(stockCandles[i].c!![1])
            }catch (e: Exception){
                prices.add(0F)
            }
        }
        return prices
    }

    fun parseDayChangesFromStockCandles(stockCandles: MutableList<StockCandles>): MutableList<Float> {
        val dayChanges: MutableList<Float> = mutableListOf()
        var dayChange: Float
        for (i in 0 until stockCandles.size) {
            try {//get dayChange in percents
                dayChange = (stockCandles[i].c!![1] * 100 / stockCandles[i].c!![0]) - 100F
                dayChanges.add(String.format("%.2f", dayChange).toFloat())
            }catch (e: Exception){
                dayChanges.add(0F)
            }
        }
        return dayChanges
    }

    fun convertArraysToShares(tickers: List<String>, names: List<String>, prices: List<Float?>, dayChanges: List<Float?>, logos: List<String>): List<Share> {
        val shares: MutableList<Share> = mutableListOf()
        for(i in tickers.indices){
            shares.add(Share(tickers[i], names[i], prices[i]!!, dayChanges[i]!!, logos[i]))
        }
        return shares
    }
}