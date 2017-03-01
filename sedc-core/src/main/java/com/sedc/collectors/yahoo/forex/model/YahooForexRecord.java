package com.sedc.collectors.yahoo.forex.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by SuperOleg on 01.03.2017.
 */
@Data
public class YahooForexRecord {

    private String id;
    private String name;
    private Double Rate;
    private Date date;
    private Date time;
    private Double ask;
    private Double bid;
}
