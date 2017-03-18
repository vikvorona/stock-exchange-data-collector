package com.sedc.core.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Cacheable
@Table(name = "SOURCE_CENTER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SourceCenter {

    private Long scId;
    private String name;
    private String description;
    private Timestamp lastUpdateTm;
    private Boolean activeFlag;

    public SourceCenter() {
    }

    public SourceCenter(Long scId) {
        this.scId = scId;
    }

    @Id
    @SequenceGenerator(name = "SOURCE_CENTER_GEN", sequenceName = "S_SOURCE_CENTER_PK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOURCE_CENTER_GEN")
    @Column(name = "SC_ID")
    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
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

    @Column(name = "LAST_UPDATE_TM")
    public Timestamp getLastUpdateTm() {
        return lastUpdateTm;
    }

    public void setLastUpdateTm(Timestamp lastUpdateTm) {
        this.lastUpdateTm = lastUpdateTm;
    }

    @Column(name = "ACTIVE_FLAG")
    @Type(type = "yes_no")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SourceCenter that = (SourceCenter) o;

        return new EqualsBuilder()
                .append(scId, that.scId)
                .append(name, that.name)
                .append(description, that.description)
                .append(lastUpdateTm, that.lastUpdateTm)
                .append(activeFlag, that.activeFlag)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(scId)
                .append(name)
                .append(description)
                .append(lastUpdateTm)
                .append(activeFlag)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SourceCenter{" + "name=" + name + '}';
    }
}
