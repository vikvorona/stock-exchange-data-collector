package com.sedc.collectors.yahoo.historical;

import com.sedc.collectors.yahoo.historical.model.YahooHistoricalRecord;
import com.sedc.collectors.yahoo.util.YahooResourceHelper;
import com.sedc.collectors.yahoo.util.YahooResultReader;
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
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configurable
public class YahooHistoricalApplication {

    private static final Logger LOG = Logger.getLogger(YahooHistoricalApplication.class);

    private JobLauncher jobLauncher;
    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;
    private YahooResultReader<YahooHistoricalRecord> reader;
    private YahooHistoricalProcessor processor;
    private FlatFileItemWriter<StageYahooHistorical> writer;

    @Autowired
    public YahooHistoricalApplication(JobLauncher jobLauncher,
                                    StepBuilderFactory stepBuilderFactory,
                                    JobBuilderFactory jobBuilderFactory,
                                    YahooResultReader<YahooHistoricalRecord> reader,
                                    YahooHistoricalProcessor processor,
                                    FlatFileItemWriter<StageYahooHistorical> writer) {
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

        //TODO: acquire proper start and end dates;
        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();

        UrlResource resource = YahooResourceHelper.getHistoricalDataResource(symbols, startDate, endDate);
        reader.setResource(resource);

        TaskletStep step = stepBuilderFactory.get("yahooHistoricalStep")
                .<YahooHistoricalRecord, StageYahooHistorical> chunk(1)
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

    private UrlResource getResource(List<String> symbols) throws MalformedURLException {
        //TODO: implement properly
        return new UrlResource("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance" +
                ".historicaldata%20where%20symbol%20%3D%20%22YHOO%22%20and%20startDate%20%3D%20%222009-09-11%22%20" +
                "and%20endDate%20%3D%20%222010-03-10%22&format=json&env=store%3A%2F%2Fdatatables.org%2" +
                "Falltableswithkeys");
    }
}
