package com.sedc.collectors.yahoo.historical;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class YahooHistoricalApplication {

    public static void main(String[] args) throws Exception {
        CommandLineJobRunner.main(new String[]{"spring/batch/jobs/yahoo/yahoo-historical-load-job.xml",
                "yahooHistoricalLoadJob"});
    }
}
