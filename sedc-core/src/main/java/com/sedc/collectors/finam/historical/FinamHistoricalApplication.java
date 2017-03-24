package com.sedc.collectors.finam.historical;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class FinamHistoricalApplication {

    public static void main(String[] args) throws Exception {
//        if (args == null || args.length != 2) {
//            LOG.info("USAGE: <period> <region>"); //TODO: make usage
//            return;
//        }

        CommandLineJobRunner.main(new String[]{"spring/batch/jobs/finam/finam-historical-load-job.xml","finam-historical-load-job"});
    }
}
