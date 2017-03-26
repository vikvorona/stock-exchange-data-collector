package com.sedc.core.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Cacheable
@Table(name = "SNAPSHOT_QUOTE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class SnapshotQuote {

    @Id
    @SequenceGenerator(name = "SNAPSHOT_QUOTE_GEN", sequenceName = "s_snapshot_quote_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SNAPSHOT_QUOTE_GEN")
    @Column(name = "SQ_ID")
    private BigDecimal id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCEI_ID")
    private SourceCenterEngineInstance scei;

    @Column(name = "BUSINESS_DATE")
    private Date businessDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYM_ID")
    private Symbol symId;

    @Column(name = "AVG_DAILY_VOLUME")
    private Double avgDailyVolume;

    @Column(name = "CHANGE")
    private Double change;

    @Column(name = "DAYS_LOW")
    private Double daysLow;

    @Column(name = "DAYS_HIGH")
    private Double daysHigh;

    @Column(name = "YEARS_LOW")
    private Double yearsLow;

    @Column(name = "YEARS_HIGH")
    private Double yearsHigh;

    @Column(name = "MARKET_CAPITALIZATION")
    private Double marketCapitalization;

    @Column(name = "LAST_TRADE_PRICE")
    private Double lastTradePrice;

    @Column(name = "DAYS_RANGE")
    private Double daysRange;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VOLUME")
    private Long volume;

}
