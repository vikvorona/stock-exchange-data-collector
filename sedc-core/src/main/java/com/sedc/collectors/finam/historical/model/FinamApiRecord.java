package com.sedc.collectors.finam.historical.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;


/**
 * Created by oshulyakov on 2/28/2017.
 */
public class FinamApiRecord {

    private String ticker;
    private String period;
    private Date date;
    private Date time;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof FinamApiRecord)) {
            return false;
        }

        FinamApiRecord user = (FinamApiRecord) o;

        return new EqualsBuilder()
                .append(ticker, user.ticker)
                .append(period, user.period)
                .append(date, user.date)
                .append(time, user.time)
                .append(open, user.open)
                .append(high, user.high)
                .append(low, user.low)
                .append(close, user.close)
                .append(volume, user.ticker)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(ticker)
                .append(period)
                .append(date)
                .append(time)
                .append(open)
                .append(high)
                .append(low)
                .append(close)
                .append(volume)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(ticker)
                .append(period)
                .append(date)
                .append(time)
                .append(open)
                .append(high)
                .append(low)
                .append(close)
                .append(volume)
                .build();
    }
}
