package com.sedc.core.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Cacheable
@Table(name = "STAGE_FINAM_HISTORICAL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class StageFinamHistorical {

    private Long id;
    private Long hash;
    private Symbol symbol;
    private String ticket;
    private String period;
    private Date date;
    private Date time;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private Boolean activeFlag;
    private String activeReason;

    public StageFinamHistorical() {
    }

    public StageFinamHistorical(Long id) {
        this.id = id;
    }

    @Id
    @SequenceGenerator(name = "STAGE_FINAM_HISTORICAL_GEN", sequenceName = "S_STAGE_FINAM_HISTORICAL_PK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STAGE_FINAM_HISTORICAL_GEN")
    @Column(name = "SFH_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "HASH")
    public Long getHash() {
        return hash;
    }

    public void setHash(Long hash) {
        this.hash = hash;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYMBOL")
    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Column(name = "TICKET")
    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Column(name = "PER")
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
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Column(name = "ACTIVE_REASON")
    public String getActiveReason() {
        return activeReason;
    }

    public void setActiveReason(String activeReason) {
        this.activeReason = activeReason;
    }
}
