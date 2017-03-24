package com.sedc.collectors.yahoo.quote.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class YahooQuoteRecord {
    @JsonProperty("Symbol")
    private String symbol;
    @JsonProperty("AverageDailyVolume")
    private Double averageDailyVolume;
    @JsonProperty("Change")
    private Double change;
    @JsonProperty("DaysLow")
    private Double daysLow;
    @JsonProperty("DaysHigh")
    private Double daysHigh;
    @JsonProperty("YearLow")
    private Double yearLow;
    @JsonProperty("YearHigh")
    private Double yearHigh;
    @JsonProperty("MarketCapitalization")
    private String marketCapitalization;
    @JsonProperty("LastTradePriceOnly")
    private Double lastTradePriceOnly;
    @JsonProperty("DaysRange")
    private String daysRange;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("StockSymbol")
    private String stockSymbol;
    @JsonProperty("Volume")
    private Double volume;
    @JsonProperty("StockExchange")
    private String stockExchange;
}
