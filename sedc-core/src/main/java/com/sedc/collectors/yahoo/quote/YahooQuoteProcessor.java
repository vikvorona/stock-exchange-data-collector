package com.sedc.collectors.yahoo.quote;

import com.sedc.collectors.yahoo.quote.model.YahooQuoteRecord;
import com.sedc.core.model.StageYahooQuote;
import org.springframework.batch.item.ItemProcessor;

public class YahooQuoteProcessor implements ItemProcessor<YahooQuoteRecord, StageYahooQuote> {
    @Override
    public StageYahooQuote process(YahooQuoteRecord yahooQuoteRecord) throws Exception {
        StageYahooQuote stageYahooQuote = new StageYahooQuote();
        stageYahooQuote.setSymbol(yahooQuoteRecord.getSymbol());
        stageYahooQuote.setAverageDailyVolume(yahooQuoteRecord.getAverageDailyVolume());
        stageYahooQuote.setChange(yahooQuoteRecord.getChange());
        stageYahooQuote.setDaysHigh(yahooQuoteRecord.getDaysHigh());
        stageYahooQuote.setDaysLow(yahooQuoteRecord.getDaysLow());
        stageYahooQuote.setLastTradePriceOnly(yahooQuoteRecord.getLastTradePriceOnly());
        stageYahooQuote.setName(yahooQuoteRecord.getName());
        stageYahooQuote.setStockExchange(yahooQuoteRecord.getStockExchange());
        stageYahooQuote.setStockSymbol(yahooQuoteRecord.getStockSymbol());
        stageYahooQuote.setVolume(yahooQuoteRecord.getVolume());
        stageYahooQuote.setYearHigh(yahooQuoteRecord.getYearHigh());
        stageYahooQuote.setYearLow(yahooQuoteRecord.getYearLow());
        return stageYahooQuote;
    }
}
