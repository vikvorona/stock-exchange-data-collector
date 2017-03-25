package com.sedc.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Cacheable
@Table(name = "EXCHANGE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Exchange {

    private Long id;
    private String name;
    private String description;
    private String country;
    private CodeGeneric region;
    private Integer timeZone;
    private Timestamp lastUpdateTm;

    public Exchange() {
    }

    public Exchange(Long id) {
        this.id = id;
    }

    @Id
    @SequenceGenerator(name = "EXCHANGE_GEN", sequenceName = "s_exchange_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXCHANGE_GEN")
    @Column(name = "EX_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "REGION_CG_ID")
    public CodeGeneric getRegion() {
        return region;
    }

    public void setRegion(CodeGeneric region) {
        this.region = region;
    }

    @Column(name = "TIMEZONE_UTC_DIFF")
    public Integer getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Integer timeZone) {
        this.timeZone = timeZone;
    }

    @Column(name = "LAST_UPDATE_TM")
    public Timestamp getLastUpdateTm() {
        return lastUpdateTm;
    }

    public void setLastUpdateTm(Timestamp lastUpdateTm) {
        this.lastUpdateTm = lastUpdateTm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Exchange exchange = (Exchange) o;

        return new EqualsBuilder()
                .append(name, exchange.name)
                .append(country, exchange.country)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(country)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "name='" + name + '\'' +
                '}';
    }
}
