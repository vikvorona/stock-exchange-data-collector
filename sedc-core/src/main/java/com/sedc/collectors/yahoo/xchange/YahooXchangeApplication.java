package com.sedc.collectors.yahoo.xchange;

import com.sedc.Region;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;

public class YahooXchangeApplication {

    public static void main(String[] args) throws Exception {

        if (args == null || args.length != 1) {
            System.out.println("USAGE: <region>");
            return;
        }

        Region region = Region.valueOf(args[0]);

        CommandLineJobRunner.main(new String[]{"spring/batch/jobs/yahoo/yahoo-xchange-load-job.xml", "yahooXchangeLoadJob", "region=" + region});
    }
}
