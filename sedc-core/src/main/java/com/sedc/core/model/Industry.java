package com.sedc.core.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Cacheable
@Table(name = "INDUSTRY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Industry {

    private Integer id;

    private String name;

    private String sector;

    public Industry() {
    }

    public Industry(Integer id) {
        this.id = id;
    }

    public Industry(Integer id, String name, String sector) {
        this.id = id;
        this.name = name;
        this.sector = sector;
    }

    @Id
    @Column(name = "IND_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "SECTOR")
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Industry industry = (Industry) o;

        return new EqualsBuilder()
                .append(name, industry.name)
                .append(sector, industry.sector)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(sector)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("id", id)
                .append("name", name)
                .append("sector", sector)
                .toString();
    }
}
