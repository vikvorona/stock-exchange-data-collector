package com.sedc.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Cacheable
@Table(name = "SNAPSHOT_HISTORICAL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SnapshotHistorical {

    private Long id;
    private SourceCenterEngineInstance sceInstance;
    private Date businessDate;
    private Symbol symbol;
    private String period;
    private Date date;
    private Date time; // TODO think about it
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private Boolean activeFlag;

    public SnapshotHistorical() {
    }

    public SnapshotHistorical(Long id) {
        this.id = id;
    }

    @Id
    @SequenceGenerator(name = "SNAPSHOT_HISTORICAL_GEN", sequenceName = "s_snapshot_historical_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SNAPSHOT_HISTORICAL_GEN")
    @Column(name = "SH_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCEI_ID")
    public SourceCenterEngineInstance getSceInstance() {
        return sceInstance;
    }

    public void setSceInstance(SourceCenterEngineInstance sceInstance) {
        this.sceInstance = sceInstance;
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
    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Column(name = "PERIOD")
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Column(name = "DATE")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "TIME")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "OPEN")
    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    @Column(name = "HIGH")
    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    @Column(name = "LOW")
    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    @Column(name = "CLOSE")
    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    @Column(name = "VOLUME")
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Column(name = "ACTIVE_FLAG")
    @Type(type = "yes_no")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SnapshotHistorical that = (SnapshotHistorical) o;

        return new EqualsBuilder()
                .append(sceInstance, that.sceInstance)
                .append(businessDate, that.businessDate)
                .append(symbol, that.symbol)
                .append(period, that.period)
                .append(date, that.date)
                .append(time, that.time)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sceInstance)
                .append(businessDate)
                .append(symbol)
                .append(period)
                .append(date)
                .append(time)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sceInstance", sceInstance)
                .append("businessDate", businessDate)
                .append("symbol", symbol)
                .append("period", period)
                .append("date", date)
                .append("time", time)
                .toString();
    }
}
