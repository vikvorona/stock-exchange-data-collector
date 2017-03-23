package com.sedc.collectors.yahoo.historical.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class YahooHistoricalRecord {
    @JsonProperty("Symbol")
    private String symbol;
    @JsonProperty("Date")
    private Date date;
    @JsonProperty("Open")
    private Double open;
    @JsonProperty("High")
    private Double high;
    @JsonProperty("Low")
    private Double low;
    @JsonProperty("Close")
    private Double close;
    @JsonProperty("Volume")
    private Double volume;
    @JsonProperty("Adj_Close")
    private Double adjClose;
}
