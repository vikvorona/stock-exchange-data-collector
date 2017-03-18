package com.sedc.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;


@Entity
@Cacheable
@Table(name = "SOURCE_CENTER_ENGINE_INSTANCE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SourceCenterEngineInstance {

    private Long id;

    private SourceCenterEngine sourceCenterEngine;

    private Date businessDate;

    private CodeGeneric state;

    private CodeGeneric detailState;

    private Date startTime;

    private Date endTime;

    public SourceCenterEngineInstance() {
    }

    public SourceCenterEngineInstance(Long id) {
        this.id = id;
    }

    public SourceCenterEngineInstance(Long id,
                                      SourceCenterEngine sourceCenterEngine,
                                      Date businessDate,
                                      CodeGeneric state,
                                      CodeGeneric detailState,
                                      Date startTime,
                                      Date endTime) {
        this.id = id;
        this.sourceCenterEngine = sourceCenterEngine;
        this.businessDate = businessDate;
        this.state = state;
        this.detailState = detailState;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Id
    @SequenceGenerator(name = "SOURCE_CENTER_ENGINE_INSTANCE_GEN",
            sequenceName = "s_source_center_engine_instance_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SOURCE_CENTER_ENGINE_INSTANCE_GEN")
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
    public CodeGeneric getState() {
        return state;
    }

    public void setState(CodeGeneric state) {
        this.state = state;
    }

    @Column(name = "SCEI_DETAIL_STATE_CG_ID")
    public CodeGeneric getDetailState() {
        return detailState;
    }

    public void setDetailState(CodeGeneric detailState) {
        this.detailState = detailState;
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
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SourceCenterEngineInstance that = (SourceCenterEngineInstance) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(sourceCenterEngine, that.sourceCenterEngine)
                .append(businessDate, that.businessDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(sourceCenterEngine)
                .append(businessDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("sourceCenterEngine", sourceCenterEngine)
                .append("businessDate", businessDate)
                .toString();
    }
}
