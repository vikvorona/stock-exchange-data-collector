package com.sedc.collectors.finam.symbol;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class FinamSymbolLoadApplication {

    public static void main(String[] args) throws Exception {
        CommandLineJobRunner.main(new String[]{"spring/batch/jobs/finam/finam-symbol-load-job.xml", "finam-symbol-load-job"});
    }
}
