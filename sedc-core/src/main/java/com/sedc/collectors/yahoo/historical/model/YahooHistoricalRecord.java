package com.sedc.collectors.yahoo.historical.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by SuperOleg on 01.03.2017.
 */
@Data
public class YahooHistoricalRecord {

    private String symbol;
    private Date date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private Double adjClose;
}
