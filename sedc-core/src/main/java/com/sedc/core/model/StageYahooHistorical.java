package com.sedc.core.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "STAGE_YAHOO_HISTORICAL")
public class StageYahooHistorical {
    private Long id;
    private Symbol symbol;
    private LocalDateTime createdDateTime;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private Double adjClose;

    @Id
    @SequenceGenerator(name = "STAGE_YAHOO_HISTORICAL_GEN", sequenceName = "s_stage_yahoo_historical_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STAGE_YAHOO_HISTORICAL_GEN")
    @Column(name = "SYH_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "SYM_ID")
    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Column(name = "CREATION_TM")
    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
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

    @Column(name = "ADJ_CLOSE")
    public Double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(Double adjClose) {
        this.adjClose = adjClose;
    }
}
