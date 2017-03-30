package com.sedc.collectors.yahoo.quote.model;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "quote")
@XmlAccessorType(XmlAccessType.FIELD)
public class YahooQuoteRecord {
    @XmlAttribute(name = "Symbol")
    private String symbol;
    @XmlElement(name = "AverageDailyVolume")
    private Double averageDailyVolume;
    @XmlElement(name = "Change")
    private Double change;
    @XmlElement(name = "DaysLow")
    private Double daysLow;
    @XmlElement(name = "DaysHigh")
    private Double daysHigh;
    @XmlElement(name = "YearLow")
    private Double yearLow;
    @XmlElement(name = "YearHigh")
    private Double yearHigh;
    @XmlElement(name = "MarketCapitalization")
    private String marketCapitalization;
    @XmlElement(name = "LastTradePriceOnly")
    private Double lastTradePriceOnly;
    @XmlElement(name = "DaysRange")
    private String daysRange;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "StockSymbol")
    private String stockSymbol;
    @XmlElement(name = "Volume")
    private Double volume;
    @XmlElement(name = "StockExchange")
    private String stockExchange;
}
