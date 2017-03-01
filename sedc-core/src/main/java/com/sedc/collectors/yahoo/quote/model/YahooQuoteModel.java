package com.sedc.collectors.yahoo.quote.model;

import lombok.Data;

/**
 * Created by SuperOleg on 01.03.2017.
 */
@Data
public class YahooQuoteModel {

    private String symbol;
    private Double averageDailyVolume;
    private Double change;
    private Double daysLow;
    private Double daysHigh;
    private Double yearLow;
    private Double yearHigh;
    private Double marketCapitalization;
    private Double lastTradePriceOnly;
    private Integer DaysRange;
    private String name;
    private String stockSymbol; //todo: find out what is it
    private Double volume;
    private String stockExchange;
}
