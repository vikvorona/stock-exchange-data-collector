package com.sedc.collectors.yahoo.quote;

import com.sedc.collectors.yahoo.historical.YahooHistoricalProcessor;
import com.sedc.core.model.StageYahooHistorical;
import org.springframework.batch.item.ItemProcessor;

public class YahooQuoteProcessor implements ItemProcessor<YahooHistoricalProcessor, StageYahooHistorical> {
    @Override
    public StageYahooHistorical process(YahooHistoricalProcessor item) throws Exception {
        throw new UnsupportedOperationException("Later");
    }
}
