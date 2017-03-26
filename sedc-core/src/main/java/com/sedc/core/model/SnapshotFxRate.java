package com.sedc.core.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Entity
@Cacheable
@Table(name = "SNAPSHOT_FXRATE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class SnapshotFxRate {

    @Id
    @SequenceGenerator(name = "SNAPSHOT_FXRATE_GEN", sequenceName = "s_snapshot_fxrate_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SNAPSHOT_FXRATE_GEN")
    @Column(name = "SX_ID")
    private BigDecimal sxId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCEI_ID")
    private SourceCenterEngineInstance scei;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYM_ID")
    private Symbol symId;

    @Column(name = "BUSINESS_DATE")
    private Date businessDate;

    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "TIME")
    private Time time;

    @Column(name = "ASK")
    private BigDecimal ask;

    @Column(name = "BID")
    private BigDecimal bid;

}