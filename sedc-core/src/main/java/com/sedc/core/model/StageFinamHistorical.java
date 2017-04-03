package com.sedc.core.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StageFinamHistorical {

    private Long hash;
    private String ticket;
    private BigDecimal symbol;
    private String period;
    private Date date;
    private Date time;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private Boolean activeFlag;
    private String activeReason;

}
