package com.sedc.core.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Cacheable
@Table(name = "SOURCE_CENTER_ENGINE_INSTANCE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SourceCenterEngineInstance {
    private Long id;
    private SourceCenterEngine sourceCenterEngine;
    private Date businessDate;
    private CodeGeneric codeGeneric;
    private Long detailStateId;
    private Date startTime;
    private Date endTime;

    public SourceCenterEngineInstance() {
    }

    public SourceCenterEngineInstance(Long id) {
        this.id = id;
    }

    public SourceCenterEngineInstance(Long id, SourceCenterEngine sourceCenterEngine, Date businessDate,
                                      CodeGeneric codeGeneric, Long detailStateId, Date startTime, Date endTime) {
        this.id = id;
        this.sourceCenterEngine = sourceCenterEngine;
        this.businessDate = businessDate;
        this.codeGeneric = codeGeneric;
        this.detailStateId = detailStateId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Id
    @SequenceGenerator(name = "SOURCE_CENTER_ENGINE_INSTANCE_GEN", sequenceName = "s_source_center_engine_instance_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOURCE_CENTER_ENGINE_INSTANCE_GEN")
    @Column(name = "SCEI_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCE_ID")
    public SourceCenterEngine getSourceCenterEngine() {
        return sourceCenterEngine;
    }

    public void setSourceCenterEngineId(SourceCenterEngine sourceCenterEngine) {
        this.sourceCenterEngine = sourceCenterEngine;
    }

    @Column(name = "BUSINESS_DATE")
    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCEI_STATE_CG_ID")
    public CodeGeneric getCodeGeneric() {
        return codeGeneric;
    }

    public void setCodeGeneric(CodeGeneric codeGeneric) {
        this.codeGeneric = codeGeneric;
    }

    @Column(name = "SCEI_DETAIL_STATE_CG_ID")
    public Long getDetailStateId() {
        return detailStateId;
    }

    public void setDetailStateId(Long detailStateId) {
        this.detailStateId = detailStateId;
    }

    @Column(name = "START_TM")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "END_TM")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SourceCenterEngineInstance that = (SourceCenterEngineInstance) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "source center: " + sourceCenterEngine.getName() + " code: " + codeGeneric.getName() + " date: " + businessDate;
    }
}
