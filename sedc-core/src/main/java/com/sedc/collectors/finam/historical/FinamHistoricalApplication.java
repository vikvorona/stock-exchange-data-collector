package com.sedc.collectors.finam.historical;

import com.sedc.Region;
import com.sedc.collectors.finam.FinamPeriod;
import org.apache.log4j.Logger;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class FinamHistoricalApplication {

    private static final Logger LOG = Logger.getLogger(FinamHistoricalApplication.class);

    public static void main(String[] args) throws Exception {
        if (args == null || args.length != 2) {
            LOG.info("USAGE: <period> <region>"); //TODO: make usage
            return;
        }

        FinamPeriod period = FinamPeriod.getInstance(args[0]);
        Region region = Region.valueOf(args[1]);

        CommandLineJobRunner.main(new String[]{"spring/batch/jobs/finam/finam-historical-load-job.xml", "finam-historical-load-job",
                "period=" + period.getCode(), "region=" + region});
    }
}
