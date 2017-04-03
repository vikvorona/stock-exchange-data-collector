package com.sedc.snapshot.yahoo.historical;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class YahooSnapshotHistoricalApplication {
	public static void main(String[] args) throws Exception {
		CommandLineJobRunner.main(new String[]{"spring/batch/jobs/yahoo/yahoo-snapshot-historical-load-job.xml", "yahooSnapshotHistoricalLoadJob"});
	}
}
