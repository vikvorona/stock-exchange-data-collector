package com.sedc.symbolload;

import org.apache.log4j.Logger;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class SymbolLoadApplication {

    private static final Logger LOG = Logger.getLogger(SymbolLoadApplication.class);

    public static void main(String[] args) throws Exception {
        if (args.length != 1){
            LOG.error("<EXCHANGE> parameter is required");
            System.exit(1);
        }

        String exchange = args[0];

        switch (exchange){
            case "MICEX":
                CommandLineJobRunner.main(new String[]{"spring/batch/jobs/symbol-load-micex-job.xml", "symbol-load-micex-job"});
                break;
            case "SPBEX":
                CommandLineJobRunner.main(new String[]{"spring/batch/jobs/symbol-load-spbex-job.xml", "symbol-load-spbex-job"});
                break;
            default:
                LOG.error(exchange + " is not realized");
                System.exit(1);
        }
    }
}
