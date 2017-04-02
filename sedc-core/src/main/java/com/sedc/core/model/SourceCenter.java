package com.sedc.core.model;


import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Cacheable
@Table(name = "SOURCE_CENTER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class SourceCenter {

    @Id
    @SequenceGenerator(name = "SOURCE_CENTER_GEN", sequenceName = "S_SOURCE_CENTER_PK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOURCE_CENTER_GEN")
    @Column(name = "SC_ID")
    private BigDecimal id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LAST_UPDATE_TM")
    private Timestamp lastUpdateTm;

    @Column(name = "ACTIVE_FLAG")
    @Type(type = "yes_no")
    private Boolean activeFlag;

}
