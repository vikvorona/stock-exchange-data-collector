package com.sedc.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Cacheable
@Table(name = "STAGE_YAHOO_FXRATE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class StageYahooFxrate {

    private Long syxId;

    private Long hash;

    private Long id;

    private String name;

    private Double rate;

    private Date date;

    private Date time;

    private Double ask;

    private Long bid;

    private Symbol symId;

    public StageYahooFxrate() {
    }

    public StageYahooFxrate(Long syxId) {
        this.syxId = syxId;
    }

    @Id
    @SequenceGenerator(name = "STAGE_YAHOO_FXRATE_GEN",
            sequenceName = "s_stage_yahoo_fxrate_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "STAGE_YAHOO_FXRATE_GEN")
    @Column(name = "SYX_ID")
    public Long getSyxId() {
        return syxId;
    }

    public void setSyxId(Long syxId) {
        this.syxId = syxId;
    }

    @Column(name = "HASH")
    public Long getHash() {
        return hash;
    }

    public void setHash(Long hash) {
        this.hash = hash;
    }

    @Column(name = "ID")
    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "RATE")
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
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

    @Column(name = "ASK")
    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    @Column(name = "BID")
    public Long getBId() {
        return bid;
    }

    public void setBId(Long bid) {
        this.bid = bid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYM_ID")
    public Symbol getSymId() {
        return symId;
    }

    public void setSymId(Symbol symId) {
        this.symId = symId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object == null || getClass() != object.getClass()) return false;

        StageYahooFxrate that = (StageYahooFxrate) object;

        return new EqualsBuilder()
                .appendSuper(super.equals(object))
                .append(name, that.name)
                .append(date, that.date)
                .append(time, that.time)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(name)
                .append(date)
                .append(time)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "StageYahooFxrate{" +
                "name='" + name + '\'' +
                ", rate=" + rate +
                ", date=" + date +
                '}';
    }
}
