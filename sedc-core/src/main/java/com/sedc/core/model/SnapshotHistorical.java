package com.sedc.core.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Entity
@Cacheable
@Table(name = "SNAPSHOT_HISTORICAL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class SnapshotHistorical {

    @Id
    @SequenceGenerator(name = "SNAPSHOT_HISTORICAL_GEN", sequenceName = "s_snapshot_historical_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SNAPSHOT_HISTORICAL_GEN")
    @Column(name = "SH_ID")
    private BigDecimal id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCEI_ID")
    private SourceCenterEngineInstance sceInstance;

    @Column(name = "BUSINESS_DATE")
    private Date businessDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYM_ID")
    private Symbol symbol;

    @Column(name = "PERIOD")
    private String period;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "TIME")
    private Time time; // TODO think about it

    @Column(name = "OPEN")
    private BigDecimal open;

    @Column(name = "HIGH")
    private BigDecimal high;

    @Column(name = "LOW")
    private BigDecimal low;

    @Column(name = "CLOSE")
    private BigDecimal close;

    @Column(name = "VOLUME")
    private BigDecimal volume;

    @Column(name = "ACTIVE_FLAG")
    @Type(type = "yes_no")
    private Boolean activeFlag;

}
