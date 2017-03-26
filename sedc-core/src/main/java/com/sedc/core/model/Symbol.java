package com.sedc.core.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Cacheable
@Table(name = "SYMBOL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class Symbol {

    @Id
    @SequenceGenerator(name = "SYMBOL_GEN", sequenceName = "s_symbol_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYMBOL_GEN")
    @Column(name = "SYM_ID")
    private BigDecimal id;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 40)
    private String description;

    @Column(name = "ISIN", length = 12)
    private String isin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASTER_SYM_ID")
    private Symbol masterSymbol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOURCE_CG_ID")
    private CodeGeneric source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EX_ID")
    private Exchange exchange;

    @Column(name = "LAST_UPDATE_TM")
    private Timestamp lastUpdateTm;

}
