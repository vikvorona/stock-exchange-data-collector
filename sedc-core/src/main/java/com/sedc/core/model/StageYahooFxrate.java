package com.sedc.core.model;

import lombok.Data;

import java.util.Date;

@Data
public class StageYahooFxrate {

    private Long hash;

    private String id;

    private String name;

    private Double rate;

    private Date date;

    private Date time;

    private Double ask;

    private Long bid;

    private Long symId;

    private Boolean activeFlag;

    private String activeReason;

}
