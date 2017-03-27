package com.sedc.collectors.yahoo.historical;

import com.sedc.collectors.yahoo.historical.model.YahooHistoricalRecord;
import com.sedc.collectors.yahoo.util.YahooResourceHelper;
import com.sedc.core.model.StageYahooHistorical;
import com.sedc.managers.SymbolManager;
import com.sedc.managers.SymbolManagerImpl;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.UrlResource;

import java.time.LocalDate;
import java.util.List;

public class YahooHistoricalApplication {

    private static final Logger LOG = Logger.getLogger(YahooHistoricalApplication.class);

    private JobLauncher jobLauncher;
    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;
    private StaxEventItemReader<YahooHistoricalRecord> reader;
    private ItemProcessor<YahooHistoricalRecord, StageYahooHistorical> processor;
    private ItemWriter<StageYahooHistorical> writer;

    public YahooHistoricalApplication(JobLauncher jobLauncher,
                                      StepBuilderFactory stepBuilderFactory,
                                      JobBuilderFactory jobBuilderFactory,
                                      StaxEventItemReader<YahooHistoricalRecord> reader,
                                      ItemProcessor<YahooHistoricalRecord, StageYahooHistorical> processor,
                                      ItemWriter<StageYahooHistorical> writer) {
        this.jobLauncher = jobLauncher;
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            LOG.info("USAGE: <sourceEngineName>");
            return;
        }

        ApplicationContext context =
                new ClassPathXmlApplicationContext("/spring/batch/jobs/yahoo-historical-job.xml");

        YahooHistoricalApplication yahooHistoricalApplication =
                (YahooHistoricalApplication) context.getBean("yahooHistoricalApplication");

        try {
            yahooHistoricalApplication.run(args);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void run(String[] args) throws Exception {

        String sourceEngineName = args[0];
        SymbolManager manager = new SymbolManagerImpl();
        List<String> symbols = manager.getStringSymbolsBySource(sourceEngineName);

        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();

        UrlResource resource = YahooResourceHelper.getHistoricalDataResource(symbols, startDate, endDate);
        reader.setResource(resource);

        TaskletStep step = stepBuilderFactory.get("yahooHistoricalStep")
                .<YahooHistoricalRecord, StageYahooHistorical>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

        Job job = jobBuilderFactory.get("yahooHistoricalLoad")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();

        JobExecution execution = jobLauncher.run(job, new JobParameters());
        LOG.info("Exit Status : " + execution.getStatus());
    }
}
