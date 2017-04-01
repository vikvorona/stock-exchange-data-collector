package com.sedc.core.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Cacheable
@Table(name = "EXCHANGE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class Exchange {

    @Id
    @SequenceGenerator(name = "EXCHANGE_GEN", sequenceName = "s_exchange_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXCHANGE_GEN")
    @Column(name = "EX_ID")
    private BigDecimal id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COUNTRY")
    private String country;

    @ManyToOne
    @JoinColumn(name = "REGION_CG_ID")
    private CodeGeneric region;

    @Column(name = "TIMEZONE_UTC_DIFF")
    private BigDecimal timeZone;

    @Column(name = "OPEN_LOCAL")
    private Time openLocal;

    @Column(name = "CLOSE_LOCAL")
    private Time closeLocal;

    @Column(name = "LAST_UPDATE_TM")
    private Timestamp lastUpdateTm = new Timestamp(new Date().getTime());

}
