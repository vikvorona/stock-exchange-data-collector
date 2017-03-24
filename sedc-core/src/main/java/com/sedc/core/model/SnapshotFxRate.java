package com.sedc.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

@Entity
@Cacheable
@Table(name = "SNAPSHOT_FXRATE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SnapshotFxRate {

    private Long sxId;

    private SourceCenterEngineInstance scei;

    private Symbol symId;

    private Date businessDate;

    private String id;

    private String name;

    private Date date;

    private Date time;

    private Double ask;

    private Double bid;

    public SnapshotFxRate() {
    }

    public SnapshotFxRate(Long sxId) {
        this.sxId = sxId;
    }

    @Id
    @SequenceGenerator(name = "SNAPSHOT_FXRATE_GEN", sequenceName = "s_snapshot_fxrate_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SNAPSHOT_FXRATE_GEN")
    @Column(name = "SX_ID")
    public Long getSxId() {
        return sxId;
    }

    public void setSxId(Long sxid) {
        this.sxId = sxid;
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
    public Symbol getSymId() {
        return symId;
    }

    public void setSymId(Symbol symId) {
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    @Column(name = "BID")
    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SnapshotFxRate that = (SnapshotFxRate) o;

        return new EqualsBuilder()
                .append(scei, that.scei)
                .append(symId, that.symId)
                .append(businessDate, that.businessDate)
                .append(id, that.id)
                .append(date, that.date)
                .append(time, that.time)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(scei)
                .append(symId)
                .append(businessDate)
                .append(id)
                .append(date)
                .append(time)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("scei", scei)
                .append("symId", symId)
                .append("businessDate", businessDate)
                .append("id", id)
                .append("date", date)
                .append("time", time)
                .toString();
    }
}