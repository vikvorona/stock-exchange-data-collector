package com.sedc.core.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Cacheable
@Table(name = "INDUSTRY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class Industry {

    @Id
    @Column(name = "IND_ID")
    private BigDecimal id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SECTOR")
    private String sector;

}
