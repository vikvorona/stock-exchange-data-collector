package com.sedc.collectors.yahoo.historical;

import com.sedc.collectors.yahoo.historical.model.YahooHistoricalRecord;
import com.sedc.core.model.StageYahooHistorical;
import org.springframework.batch.item.ItemProcessor;

public class YahooHistoricalProcessor implements ItemProcessor<YahooHistoricalRecord, StageYahooHistorical> {

	@Override
	public StageYahooHistorical process(YahooHistoricalRecord item) throws Exception {
		//TODO: fix entities
		StageYahooHistorical stageYahooHistorical = new StageYahooHistorical();
		stageYahooHistorical.setSymbol(item.getSymbol());
		stageYahooHistorical.setAdjClose(item.getAdjClose());
		stageYahooHistorical.setClose(item.getClose());
		stageYahooHistorical.setDate(item.getDate());
		stageYahooHistorical.setHigh(item.getHigh());
		stageYahooHistorical.setLow(item.getLow());
		stageYahooHistorical.setOpen(item.getOpen());
		stageYahooHistorical.setVolume(item.getVolume());
		return stageYahooHistorical;
	}
}
