package com.sedc.core.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Cacheable
@Table(name = "STAGE_YAHOO_FXRATE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class StageYahooFxrate {


    private Long syx_id;
    private Long hash;
    private Long id;
    private String name;
    private Double rate;
    private Date date;
    private Long time;
    private Double ask;
    private Long bid;
    private Long sym_id;

    public StageYahooFxrate() {
    }

    public StageYahooFxrate(Long syx_id) {
        this.syx_id = syx_id;
    }

    public StageYahooFxrate(Long syx_id, Long hash, Long id, String name, Double rate, Date date, Long time, Double ask, Long bid, Long sym_id) {
        this.syx_id = syx_id;
        this.hash = hash;
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.date = date;
        this.time = time;
        this.ask = ask;
        this.bid = bid;
        this.sym_id = sym_id;
    }

    @Id
    @SequenceGenerator(name = "STAGE_YAHOO_FXRATE_GEN", sequenceName = "s_stage_yahoo_fxrate_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STAGE_YAHOO_FXRATE_GEN")
    @Column(name = "SYX_ID")
    public Long getSyxId() {
        return syx_id;
    }

    public void setSyxId(Long syx_id) {
        this.syx_id = syx_id;
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

    @Column(name = "SYM_ID")
    public Long getSymId() {
        return sym_id;
    }

    public void setSymId(Long sym_id) {
        this.sym_id = sym_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StageYahooFxrate that = (StageYahooFxrate) o;
        return Objects.equals(syx_id, that.syx_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(syx_id);
    }

    @Override
    public String toString() {
        return name;
    }
}
