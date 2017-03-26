package com.sedc.core.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Cacheable
@Table(name = "SYSTEM_VARIABLE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class SystemVariable {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "VALUE")
    private String value;

}
