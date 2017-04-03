package com.sedc.snapshot.yahoo.fxrate;

import com.sedc.core.model.SnapshotFxRate;
import com.sedc.core.model.StageYahooFxrate;
import org.springframework.batch.item.ItemProcessor;


public class YahooSnapshotFxrateProcessor implements ItemProcessor<StageYahooFxrate, SnapshotFxRate> {
	@Override
	public SnapshotFxRate process(StageYahooFxrate item) throws Exception {
		//TODO: implement verification against existing snapshot contents
		return null;
	}
}
