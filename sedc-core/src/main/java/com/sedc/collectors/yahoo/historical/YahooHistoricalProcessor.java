package com.sedc.collectors.yahoo.historical;

import com.sedc.collectors.yahoo.historical.model.YahooHistoricalRecord;
import com.sedc.core.model.StageYahooHistorical;
import org.springframework.batch.item.ItemProcessor;

public class YahooHistoricalProcessor implements ItemProcessor<YahooHistoricalRecord, StageYahooHistorical> {
    @Override
    public StageYahooHistorical process(YahooHistoricalRecord yahooHistoricalRecord) throws Exception {
        StageYahooHistorical stageYahooHistorical = new StageYahooHistorical();
        stageYahooHistorical.setAdjClose(yahooHistoricalRecord.getAdjClose());
        stageYahooHistorical.setClose(yahooHistoricalRecord.getClose());
        stageYahooHistorical.setHigh(yahooHistoricalRecord.getHigh());
        stageYahooHistorical.setLow(yahooHistoricalRecord.getLow());
        stageYahooHistorical.setOpen(yahooHistoricalRecord.getOpen());
        stageYahooHistorical.setVolume(yahooHistoricalRecord.getVolume());
        return stageYahooHistorical;
    }
}
