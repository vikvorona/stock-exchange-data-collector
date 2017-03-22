package com.sedc.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

public class StageYahooFxrate {

    private Long hash;

    private String id;

    private String name;

    private Double rate;

    private Date date;

    private Date time;

    private Double ask;

    private Long bid;

    private Long symId;

    private Boolean activeFlag;

    private String activeReason;

    public Long getHash() {
        return hash;
    }

    public void setHash(Long hash) {
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public Long getSymId() {
        return symId;
    }

    public void setSymId(Long symId) {
        this.symId = symId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getActiveReason() {
        return activeReason;
    }

    public void setActiveReason(String activeReason) {
        this.activeReason = activeReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StageYahooFxrate that = (StageYahooFxrate) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(date, that.date)
                .append(time, that.time)
                .append(activeFlag, that.activeFlag)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(date)
                .append(time)
                .append(activeFlag)
                .toHashCode();
    }
}
