package com.sedc.core.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Cacheable
@Table(name = "CODE_GENERIC")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CodeGeneric {


    private Long id;
    private String type;
    private String name;
    private String description;
    private boolean activeFlag;

    public CodeGeneric() {
    }

    public CodeGeneric(Long id) {
        this.id = id;
    }

    public CodeGeneric(Long id, String type, String name, String description, boolean activeFlag) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.activeFlag = activeFlag;
    }

    @Id
    @SequenceGenerator(name = "CODE_GENERIC_GEN", sequenceName = "s_code_generic_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODE_GENERIC_GEN")
    @Column(name = "CG_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "ACTIVE_FLAG")
    @Type(type = "yes_no")
    public boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeGeneric that = (CodeGeneric) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
