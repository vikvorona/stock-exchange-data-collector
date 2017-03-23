package com.sedc.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Cacheable
@Table(name = "SNAPSHOT_FXRATE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SnapshotFxrate {


    private Long sxid;

    private SourceCenterEngineInstance scei;

    private StageYahooFxrate symId

    private Date businessDate;

    private Double id;

    private String name;

    private Date date;

    private Date time;

    private Float ask;

    private Float bid;

    public SnapshotFxrate() {
    }

    public SnapshotFxrate(Long sxid) {
        this.sxid = sxid;
    }

    @Id
    @SequenceGenerator(name = "SNAPSHOT_FXRATE_GEN", sequenceName = "s_snapshot_fxrate_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SNAPSHOT_FXRATE_GEN")
    @Column(name = "SX_ID")
    public Long getSxId() {
        return sxid;
    }

    public void setSxId(Long sxid) {
        this.sxid = sxid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCEI_ID")
    public SourceCenterEngineInstance getScei() {
        return scei;
    }

    public void setScei(SourceCenterEngineInstance scei) {
        this.scei = scei;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYM_ID")
    public StageYahooFxrate getSymId() {
        return symId;
    }

    public void setSymId(StageYahooFxrate symId) {
        this.symId = symId;
    }

    @Column(name = "BUSINESS_DATE")
    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    @Column(name = "ID")
    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public Float getAsk() {
        return ask;
    }

    public void setAsk(Float ask) {
        this.ask = ask;
    }

    @Column(name = "BID")
    public Float getBid() {
        return bid;
    }

    public void setAsk(Float bid) {
        this.bid = bid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SnapshotFxrate that = (SnapshotFxrate) o;

        return new EqualsBuilder()
                .append(scei, that.scei)
                .append(businessDate, that.businessDate)
                .append(name, that.name)
                .isEquals();
//maybe this isn't enougth
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(scei)
                .append(businessDate)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SnapshotFxrate{" +
                "businessDate=" + businessDate +
                ", id=" + id +
                ", name='" + name +
                ", date=" + date +
                ", time=" + time +
                ", ask=" + ask +
                ", bid=" + bid +
                '}';
    }
}