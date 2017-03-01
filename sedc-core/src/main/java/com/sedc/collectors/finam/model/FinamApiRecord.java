package com.sedc.collectors.finam.model;

import com.sedc.collectors.finam.FinamPeriods;
import lombok.Data;

import java.util.Date;


/**
 * Created by oshulyakov on 2/28/2017.
 */
@Data
public class FinamApiRecord {

    public String ticker;
    public FinamPeriods period;
    public Date date;
    public Date time;
    public Double open;
    public Double high;
    public Double low;
    public Double close;
    public Double volume;
}
