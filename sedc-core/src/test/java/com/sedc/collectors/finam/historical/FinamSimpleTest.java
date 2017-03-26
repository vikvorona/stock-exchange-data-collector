package com.sedc.collectors.finam.historical;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/batch/jobs/finam-job-test.xml")
public class FinamSimpleTest {

    private static final Logger LOG = Logger.getLogger(FinamSimpleTest.class);

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private FlatFileItemReader reader;
    @Autowired
    private Job job;

    @Test
    public void testFile() throws Exception {

        reader.setResource(new ClassPathResource("com/sedc/collectors/finam/historical/sample-data.csv"));

        JobExecution execution = jobLauncher.run(job, new JobParameters());
        LOG.info("Exit Status : " + execution.getStatus());
    }

    @Test
    public void testUrl() throws Exception {

        reader.setResource(new UrlResource("http://export.finam.ru/GAZP_170224_170301.txt?market=1&em=16842&code=GAZP&apply=0&df=24&mf=1&yf=2017&from=24.02.2017&dt=1&mt=2&yt=2017&to=01.03.2017&p=5&f=GAZP_170224_170301&e=.txt&cn=GAZP&dtf=1&tmf=1&MSOR=1&mstime=on&mstimever=1&sep=1&sep2=1&datf=1"));

        JobExecution execution = jobLauncher.run(job, new JobParameters());
        LOG.info("Exit Status : " + execution.getStatus());
    }
}
