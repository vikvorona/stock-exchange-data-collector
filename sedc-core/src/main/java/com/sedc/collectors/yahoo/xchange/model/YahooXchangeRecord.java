package com.sedc.collectors.yahoo.xchange.model;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "rate")
@XmlAccessorType(XmlAccessType.FIELD)
public class YahooXchangeRecord {
    @XmlAttribute(name = "id")
    private String id;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Rate")
    private Double rate;
    @XmlElement(name = "Date")
    private String date;
    @XmlElement(name = "Time")
    private String time;
    @XmlElement(name = "Ask")
    private Double ask;
    @XmlElement(name = "Bid")
    private Double bid;
}
