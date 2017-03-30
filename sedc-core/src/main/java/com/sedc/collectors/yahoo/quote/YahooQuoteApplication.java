package com.sedc.collectors.yahoo.quote;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class YahooQuoteApplication {
    public static void main(String[] args) throws Exception {
        CommandLineJobRunner.main(new String[]{"spring/batch/jobs/yahoo/yahoo-quote-load-job.xml", "yahooQuoteLoadJob"});
    }
}
