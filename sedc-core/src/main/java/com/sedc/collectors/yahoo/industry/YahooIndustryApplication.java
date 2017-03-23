package com.sedc.collectors.yahoo.industry;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class YahooIndustryApplication {

    public static void main(String[] args) throws Exception {
        CommandLineJobRunner.main(new String[]{"spring/batch/jobs/yahoo-industry-load-job.xml","yahoo-industry-load-job"});
    }
}
