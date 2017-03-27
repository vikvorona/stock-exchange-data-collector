package com.sedc.collectors.yahoo.historical.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Date;

@Data
@XmlRootElement(name="quote")
@XmlAccessorType(XmlAccessType.FIELD)
public class YahooHistoricalRecord {
    @XmlAttribute(name = "Symbol")
    private String symbol;
    @XmlElement(name = "Date")
    private Date date;
    @XmlElement(name = "Open")
    private Double open;
    @XmlElement(name = "High")
    private Double high;
    @XmlElement(name = "Low")
    private Double low;
    @XmlElement(name = "Close")
    private Double close;
    @XmlElement(name = "Volume")
    private Double volume;
    @XmlElement(name = "Adj_Close")
    private Double adjClose;
}
