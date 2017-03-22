package com.sedc.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Cacheable
@Table(name = "SYMBOL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Symbol {

    private Long id;
    private String name;
    private String description;
    private Symbol masterSymbol;
    private Exchange exchange;
    private Timestamp lastUpdateTm;

    public Symbol() {
    }

    public Symbol(Long id) {
        this.id = id;
    }

    @Id
    @SequenceGenerator(name = "SYMBOL_GEN", sequenceName = "s_symbol_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYMBOL_GEN")
    @Column(name = "SYM_ID")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASTER_SYM_ID")
    public Symbol getMasterSymbol() {
        return masterSymbol;
    }

    public void setMasterSymbol(Symbol masterSymbol) {
        this.masterSymbol = masterSymbol;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EX_ID")
    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
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

        Symbol symbol = (Symbol) o;

        return new EqualsBuilder()
                .append(name, symbol.name)
                .append(description, symbol.description)
                .append(exchange, symbol.exchange)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(description)
                .append(exchange)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "name='" + name + '\'' +
                '}';
    }
}
