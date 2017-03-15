package com.sedc.core.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Cacheable
@Table(name = "SNAPSHOT_QUOTE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SnapshotQuote {


    private Long sq_id;
    private Long scei_id;
    private Date business_date;
    private Long sym_id;
    private Double avg_daily_volume;
    private Double change;
    private Double days_low;
    private Double days_high;
    private Double years_low;
    private Double years_high;
    private Double market_capitalization;
    private Double last_trade_price;
    private Double days_range;
    private String name;
    private Long volume;

    public SnapshotQuote() {
    }

    public SnapshotQuote(Long sq_id) {
        this.sq_id = sq_id;
    }

    public SnapshotQuote(Long sq_id, Long scei_id, Date business_date, Long sym_id, Double avg_daily_volume, Double change, Double days_low, Double days_high, Double years_low, Double years_high, Double market_capitalization, Double last_trade_price, Double days_range, String name, Long volume;) {
        this.sq_id = sq_id;
        this.scei_id = scei_id;
        this.business_date = business_date;
        this.sym_id = sym_id;
        this.avg_daily_volume = avg_daily_volume;
        this.change = change;
        this.days_low = days_low;
        this.days_high = days_high;
        this.years_low = years_low;
        this.years_high = years_high;
        this.market_capitalization = market_capitalization;
        this.last_trade_price = last_trade_price;
        this.days_range = days_range;
        this.name = name;
        this.volume = volume;
    }

    @Id
    @SequenceGenerator(name = "SNAPSHOT_QUOTE_GEN", sequenceName = "s_snapshot_quote_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SNAPSHOT_QUOTE_GEN")
    @Column(name = "SQ_ID")
    public Long getSqId() {
        return sq_id;
    }

    public void setSqId(Long sq_id) {
        this.sq_id = sq_id;
    }

    @Column(name = "SCEI_ID")
    public Long getSceiId() {
        return scei_id;
    }

    public void setSceiId(Long scei_id) {
        this.scei_id = scei_id;
    }

    @Column(name = "BUSINESS_DATE")
    public Date getBusinessDate() { return business_date; }

    public void setBusinessDate(Date business_date) {
        this.business_date = business_date;
    }

    @Column(name = "SYM_ID")
    public Long getSymId() {
        return sym_id;
    }

    public void setSymId(Long sym_id) {
        this.sym_id = sym_id;
    }

    @Column(name = "AVG_DAILY_VOLUME")
    public Long getAvgDailyVolume() {
        return avg_daily_volume;
    }

    public void setAvgDailyVolume(Long avg_daily_volume) {
        this.avg_daily_volume = avg_daily_volume;
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
        return days_low;
    }

    public void setDaysL(Double days_low) {
        this.days_low = days_low;
    }

    @Column(name = "DAYS_HIGH")
    public Double getDaysH() {
        return days_high;
    }

    public void setDaysH(Double days_high) {
        this.days_high = days_high;
    }

    @Column(name = "YEARS_LOW")
    public Double getYearsL() {
        return years_low;
    }

    public void setYearsL(Double years_low) {
        this.years_low = years_low;
    }

    @Column(name = "YEARS_HIGH")
    public Double getYearsH() {
        return years_high;
    }

    public void setYearsH(Double years_high) {
        this.years_high = years_high;
    }

    @Column(name = "MARKET_CAPITALIZATION")
    public Double getMarketCapitalization() {
        return market_capitalization;
    }

    public void setMarketCapitalization(Double market_capitalization) {
        this.market_capitalization = market_capitalization;
    }

    @Column(name = "LAST_TRADE_PRICE")
    public Double getLastTradePrice() {
        return last_trade_price;
    }

    public void setLastTradePrice(Double last_trade_price) {
        this.last_trade_price = last_trade_price;
    }

    @Column(name = "DAYS_RANGE")
    public Double getDaysRange() {
        return days_range;
    }

    public void setDaysRange(Double days_range) {
        this.days_range = days_range;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnapshotQuote that = (SnapshotQuote) o;
        return Objects.equals(sq_id, that.sq_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sq_id);
    }

    @Override
    public String toString() {
        return name;
    }
}
