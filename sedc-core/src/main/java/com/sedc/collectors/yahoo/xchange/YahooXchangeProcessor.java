package com.sedc.collectors.yahoo.xchange;

import com.sedc.collectors.yahoo.xchange.model.YahooXchangeRecord;
import com.sedc.core.model.StageYahooFxrate;
import org.springframework.batch.item.ItemProcessor;

public class YahooXchangeProcessor implements ItemProcessor<YahooXchangeRecord, StageYahooFxrate> {
    @Override
    public StageYahooFxrate process(YahooXchangeRecord item) throws Exception {
        throw new UnsupportedOperationException("Later");
    }
}
