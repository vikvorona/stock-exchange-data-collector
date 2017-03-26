package com.sedc.core.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;


@Entity
@Cacheable
@Table(name = "SOURCE_CENTER_ENGINE_INSTANCE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class SourceCenterEngineInstance {

    @Id
    @SequenceGenerator(name = "SOURCE_CENTER_ENGINE_INSTANCE_GEN", sequenceName = "s_source_center_engine_instance_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOURCE_CENTER_ENGINE_INSTANCE_GEN")
    @Column(name = "SCEI_ID")
    private BigDecimal id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCE_ID")
    private SourceCenterEngine sourceCenterEngine;

    @Column(name = "BUSINESS_DATE")
    private Date businessDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCEI_STATE_CG_ID")
    private CodeGeneric state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCEI_DETAIL_STATE_CG_ID")
    private CodeGeneric detailState;

    @Column(name = "START_TM")
    private Timestamp startTime;

    @Column(name = "END_TM")
    private Timestamp endTime;

}
