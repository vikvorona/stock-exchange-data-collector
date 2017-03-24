package com.sedc.core.model;

import javax.persistence.*;

@Entity
@Table(name = "STAGE_YAHOO_QUOTE")
public class StageYahooQuote {
    private Long id;
    private Symbol symbol;
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
    private String stockSymbol;
    private Double volume;
    private String stockExchange;

    @Id
    @SequenceGenerator(name = "STAGE_YAHOO_QUOTE_GEN", sequenceName = "s_stage_yahoo_quote_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STAGE_YAHOO_QUOTE_GEN")
    @Column(name = "SYQ_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "SYM_ID")
    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Column(name = "AVG_DAILY_VOLUME")
    public Double getAverageDailyVolume() {
        return averageDailyVolume;
    }

    public void setAverageDailyVolume(Double averageDailyVolume) {
        this.averageDailyVolume = averageDailyVolume;
    }

    @Column(name = "CHARGE")
    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    @Column(name = "DAYS_LOW")
    public Double getDaysLow() {
        return daysLow;
    }

    public void setDaysLow(Double daysLow) {
        this.daysLow = daysLow;
    }

    @Column(name = "DAYS_HIGH")
    public Double getDaysHigh() {
        return daysHigh;
    }

    public void setDaysHigh(Double daysHigh) {
        this.daysHigh = daysHigh;
    }

    @Column(name = "YEAR_LOW")
    public Double getYearLow() {
        return yearLow;
    }

    public void setYearLow(Double yearLow) {
        this.yearLow = yearLow;
    }

    @Column(name = "YEAR_HIGH")
    public Double getYearHigh() {
        return yearHigh;
    }

    public void setYearHigh(Double yearHigh) {
        this.yearHigh = yearHigh;
    }

    @Column(name = "MARKET_CAPITALIZATION")
    public Double getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(Double marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    @Column(name = "LAST_TRADE_PRICE")
    public Double getLastTradePriceOnly() {
        return lastTradePriceOnly;
    }

    public void setLastTradePriceOnly(Double lastTradePriceOnly) {
        this.lastTradePriceOnly = lastTradePriceOnly;
    }

    @Column(name = "DAYS_RANGE")
    public Integer getDaysRange() {
        return daysRange;
    }

    public void setDaysRange(Integer daysRange) {
        this.daysRange = daysRange;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "STOCK_SYMBOL")
    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    @Column(name = "VOLUME")
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Column(name = "STOCK_EXCHANGE")
    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }
}
