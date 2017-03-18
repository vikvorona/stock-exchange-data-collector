package com.sedc.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Cacheable
@Table(name = "SNAPSHOT_QUOTE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SnapshotQuote {


    private Long sqId;

    private SourceCenterEngineInstance scei;

    private Date businessDate;

    private Symbol symId;

    private Double avgDailyVolume;

    private Double change;

    private Double daysLow;

    private Double daysHigh;

    private Double yearsLow;

    private Double yearsHigh;

    private Double marketCapitalization;

    private Double lastTradePrice;

    private Double daysRange;

    private String name;

    private Long volume;

    public SnapshotQuote() {
    }

    public SnapshotQuote(Long sqId) {
        this.sqId = sqId;
    }

    @Id
    @SequenceGenerator(name = "SNAPSHOT_QUOTE_GEN",
            sequenceName = "s_snapshot_quote_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SNAPSHOT_QUOTE_GEN")
    @Column(name = "SQ_ID")
    public Long getSqId() {
        return sqId;
    }

    public void setSqId(Long sqId) {
        this.sqId = sqId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCEI_ID")
    public SourceCenterEngineInstance getScei() {
        return scei;
    }

    public void setSceiId(SourceCenterEngineInstance scei) {
        this.scei = scei;
    }

    @Column(name = "BUSINESS_DATE")
    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYM_ID")
    public Symbol getSymId() {
        return symId;
    }

    public void setSymId(Symbol symId) {
        this.symId = symId;
    }

    @Column(name = "AVG_DAILY_VOLUME")
    public Double getAvgDailyVolume() {
        return avgDailyVolume;
    }

    public void setAvgDailyVolume(Double avgDailyVolume) {
        this.avgDailyVolume = avgDailyVolume;
    }

    @Column(name = "CHANGE")
    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    @Column(name = "DAYS_LOW")
    public Double getDaysL() {
        return daysLow;
    }

    public void setDaysL(Double daysLow) {
        this.daysLow = daysLow;
    }

    @Column(name = "DAYS_HIGH")
    public Double getDaysH() {
        return daysHigh;
    }

    public void setDaysH(Double daysHigh) {
        this.daysHigh = daysHigh;
    }

    @Column(name = "YEARS_LOW")
    public Double getYearsL() {
        return yearsLow;
    }

    public void setYearsL(Double yearsLow) {
        this.yearsLow = yearsLow;
    }

    @Column(name = "YEARS_HIGH")
    public Double getYearsH() {
        return yearsHigh;
    }

    public void setYearsH(Double yearsHigh) {
        this.yearsHigh = yearsHigh;
    }

    @Column(name = "MARKET_CAPITALIZATION")
    public Double getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(Double marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    @Column(name = "LAST_TRADE_PRICE")
    public Double getLastTradePrice() {
        return lastTradePrice;
    }

    public void setLastTradePrice(Double lastTradePrice) {
        this.lastTradePrice = lastTradePrice;
    }

    @Column(name = "DAYS_RANGE")
    public Double getDaysRange() {
        return daysRange;
    }

    public void setDaysRange(Double daysRange) {
        this.daysRange = daysRange;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "VOLUME")
    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object == null || getClass() != object.getClass()) return false;

        SnapshotQuote that = (SnapshotQuote) object;

        return new EqualsBuilder()
                .appendSuper(super.equals(object))
                .append(scei, that.scei)
                .append(businessDate, that.businessDate)
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(scei)
                .append(businessDate)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SnapshotQuote{" +
                "businessDate=" + businessDate +
                ", change=" + change +
                ", name='" + name + '\'' +
                ", volume=" + volume +
                '}';
    }
}
