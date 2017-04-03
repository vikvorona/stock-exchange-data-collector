package com.sedc.snapshot.yahoo.quote;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class YahooSnapshotQuoteApplication {
	public static void main(String[] args) throws Exception {
		CommandLineJobRunner.main(new String[]{"spring/batch/jobs/yahoo/yahoo-snapshot-quote-load-job.xml", "yahooSnapshotQuoteLoadJob"});
	}
}
