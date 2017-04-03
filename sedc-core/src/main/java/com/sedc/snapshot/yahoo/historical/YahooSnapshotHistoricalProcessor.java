package com.sedc.snapshot.yahoo.historical;


import com.sedc.core.model.SnapshotHistorical;
import com.sedc.core.model.StageYahooHistorical;
import org.springframework.batch.item.ItemProcessor;

public class YahooSnapshotHistoricalProcessor implements ItemProcessor<StageYahooHistorical, SnapshotHistorical> {
	@Override
	public SnapshotHistorical process(StageYahooHistorical item) throws Exception {
		//TODO: implement verification against existing snapshot contents
		return null;
	}
}
