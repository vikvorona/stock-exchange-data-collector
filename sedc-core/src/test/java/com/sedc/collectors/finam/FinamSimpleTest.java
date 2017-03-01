package com.sedc.collectors.finam;

import com.sedc.collectors.finam.model.FinamApiRecord;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by oshulyakov on 3/1/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/batch/jobs/finam-job-test.xml")
public class FinamSimpleTest {

    private static final Logger LOG = Logger.getLogger(FinamSimpleTest.class);

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private FlatFileItemReader reader;
    @Autowired
    private FinamApiProcessor processor;
    @Autowired
    private FlatFileItemWriter writer;

    @Test
    public void test() throws Exception {
        TaskletStep step = stepBuilderFactory.get("test step")
                .<FinamApiRecord, FinamApiRecord>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

        Job job = jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();

        JobExecution execution = jobLauncher.run(job, new JobParameters());
        LOG.info("Exit Status : " + execution.getStatus());
    }
}
