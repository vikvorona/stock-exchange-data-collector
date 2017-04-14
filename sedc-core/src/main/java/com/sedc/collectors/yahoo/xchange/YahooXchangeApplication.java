package com.sedc.collectors.yahoo.xchange;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;

/**
 * Created by SuperOleg on 01.03.2017.
 */
public class YahooXchangeApplication {

    public static void main(String[] args) throws Exception {
        CommandLineJobRunner.main(new String[]{"spring/batch/jobs/yahoo/yahoo-xchange-load-job.xml", "yahooXchangeLoadJob", "region=FOREX"});
    }
}
