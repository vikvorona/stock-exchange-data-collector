package com.sedc.core.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Cacheable
@Table(name = "CODE_GENERIC")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class CodeGeneric {

    @Id
    @SequenceGenerator(name = "CODE_GENERIC_GEN", sequenceName = "s_code_generic_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODE_GENERIC_GEN")
    @Column(name = "CG_ID")
    private BigDecimal id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACTIVE_FLAG")
    @Type(type = "yes_no")
    private boolean activeFlag;

}
