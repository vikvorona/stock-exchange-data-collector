package com.sedc.collectors.yahoo.xchange.model;

import lombok.Data;

import java.util.Date;

@Data
public class YahooXchangeRecord {
    private String id;
    private String name;
    private Double Rate;
    private Date date;
    private Date time;
    private Double ask;
    private Double bid;
}
