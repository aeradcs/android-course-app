package com.hw.app.database.data

object Data {
    fun getTop15SP500Tickers(): MutableList<String> {
        val tickers: MutableList<String> = mutableListOf()
        tickers.add("AAPL")
        tickers.add("MSFT")
        tickers.add("AMZN")
        tickers.add("FB")
        tickers.add("JPM")
        tickers.add("PFE")
        tickers.add("JNJ")
        tickers.add("GOOG")
        tickers.add("GOOGL")
        tickers.add("XOM")
        tickers.add("BAC")
        tickers.add("WFC")
        tickers.add("INTC")
        tickers.add("T")
        tickers.add("V")
        return tickers
    }
}