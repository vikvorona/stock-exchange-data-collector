package com.sedc.core.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Cacheable
@Table(name = "SOURCE_CENTER_ENGINE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class SourceCenterEngine {

    @Id
    @SequenceGenerator(name = "SOURCE_CENTER_ENGINE_GEN", sequenceName = "s_source_center_engine_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOURCE_CENTER_ENGINE_GEN")
    @Column(name = "SCE_ID")
    private BigDecimal id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SC_ID")
    private SourceCenter sourceCenter;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_CG_ID")
    private CodeGeneric region;

    @Column(name = "CURRENT_COB_SCEI")
    private BigDecimal currentScei;

    @Column(name = "LAST_UPDATE_TM")
    private Timestamp lastUpdateTime;

    @Column(name = "ACTIVE_FLAG")
    @Type(type = "yes_no")
    private Boolean activeFlag;

}
