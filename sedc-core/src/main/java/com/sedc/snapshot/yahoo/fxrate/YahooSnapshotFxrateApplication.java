package com.sedc.snapshot.yahoo.fxrate;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class YahooSnapshotFxrateApplication {
	public static void main(String[] args) throws Exception {
		CommandLineJobRunner.main(new String[]{"spring/batch/jobs/yahoo/yahoo-snapshot-fxrate-load-job.xml", "yahooSnapshotFxrateLoadJob"});
	}
}
