package com.sedc.collectors.finam.symbol.model;


import javax.xml.bind.annotation.*;

import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "value"
})
@XmlRootElement(name = "security")
@Data
public class FinamJaxbSecurity {
    @XmlValue
    protected String value;
    @XmlAttribute(name = "code")
    protected String code;
    @XmlAttribute(name = "board")
    protected String board;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "shortName")
    protected String shortName;
    @XmlAttribute(name = "priceStep")
    protected Float priceStep;
    @XmlAttribute(name = "multiplier")
    protected Integer multiplier;
    @XmlAttribute(name = "decimals")
    protected Byte decimals;
    @XmlAttribute(name = "currency")
    protected String currency;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "isin")
    protected String isin;
}
