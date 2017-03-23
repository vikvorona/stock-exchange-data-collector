package com.sedc.collectors.yahoo.quote.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YahooQuoteModel {
    @JsonProperty("Symbol")
    private String symbol;
    @JsonProperty("Average_Daily_Volume")
    private Double averageDailyVolume;
    private Double change;
    private Double daysLow;
    private Double daysHigh;
    private Double yearLow;
    private Double yearHigh;
    private Double marketCapitalization;
    private Double lastTradePriceOnly;
    private Integer daysRange;
    private String name;
    private String stockSymbol; //todo: find out what is it
    private Double volume;
    private String stockExchange;
}
