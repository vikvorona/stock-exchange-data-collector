package com.sedc.collectors.yahoo.forex;

import com.sedc.collectors.yahoo.forex.model.YahooForexRecord;
import com.sedc.core.model.StageYahooFxrate;
import org.springframework.batch.item.ItemProcessor;

public class YahooForexProcessor implements ItemProcessor<YahooForexRecord, StageYahooFxrate> {
    @Override
    public StageYahooFxrate process(YahooForexRecord item) throws Exception {
        throw new UnsupportedOperationException("Later");
    }
}
