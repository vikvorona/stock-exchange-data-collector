package com.sedc.core.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.lang.Object;
import java.util.Date;

@Entity
@Cacheable
@Table(name = "SOURCE_CENTER_ENGINE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SourceCenterEngine {

    private Long id;

    private SourceCenter sourceCenter;

    private String name;

    private CodeGeneric region;

    private Long currentScei;

    private Date lastUpdateTime;

    private Boolean activeFlag;

    public SourceCenterEngine() {
    }

    public SourceCenterEngine(Long id) {
        this.id = id;
    }

    public SourceCenterEngine(Long id, SourceCenter sourceCenter, String name,
                              CodeGeneric region, Long currentScei,
                              Date lastUpdateTime, Boolean activeFlag) {
        this.id = id;
        this.sourceCenter = sourceCenter;
        this.name = name;
        this.region = region;
        this.currentScei = currentScei;
        this.lastUpdateTime = lastUpdateTime;
        this.activeFlag = activeFlag;
    }

    @Id
    @SequenceGenerator(name = "SOURCE_CENTER_ENGINE_GEN",
            sequenceName = "s_source_center_engine_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SOURCE_CENTER_ENGINE_GEN")
    @Column(name = "SCE_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SC_ID")
    public SourceCenter getSourceCenter() {
        return sourceCenter;
    }

    public void setSourceCenter(SourceCenter sourceCenter) {
        this.sourceCenter = sourceCenter;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_CG_ID")
    public CodeGeneric getRegion() {
        return region;
    }

    public void setRegion(CodeGeneric region) {
        this.region = region;
    }

    @Column(name = "CURRENT_COB_SCEI")
    public Long getCurrentScei() {
        return currentScei;
    }

    public void setCurrentScei(Long currentScei) {
        this.currentScei = currentScei;
    }

    @Column(name = "LAST_UPDATE_TM")
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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
    public synchronized boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SourceCenterEngine that = (SourceCenterEngine) o;
        return name.equals(that.name)
                && lastUpdateTime.equals(that.lastUpdateTime);
    }

    @Override
    public synchronized int hashCode() {
        int hash = 37;
        return name.hashCode() * hash + lastUpdateTime.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
