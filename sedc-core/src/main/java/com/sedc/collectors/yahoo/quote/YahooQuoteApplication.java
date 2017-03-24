package com.sedc.collectors.yahoo.quote;

import com.sedc.collectors.yahoo.quote.model.YahooQuoteRecord;
import com.sedc.collectors.yahoo.util.YahooResourceHelper;
import com.sedc.collectors.yahoo.util.YahooResultReader;
import com.sedc.core.model.StageYahooQuote;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.UrlResource;

import java.util.List;

@Configurable
public class YahooQuoteApplication {

    private static final Logger LOG = Logger.getLogger(YahooQuoteApplication.class);

    private JobLauncher jobLauncher;
    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;
    private YahooResultReader<YahooQuoteRecord> reader;
    private ItemProcessor<YahooQuoteRecord, StageYahooQuote> processor;
    private ItemWriter<StageYahooQuote> writer;

    @Autowired
    public YahooQuoteApplication(JobLauncher jobLauncher,
                                 StepBuilderFactory stepBuilderFactory,
                                 JobBuilderFactory jobBuilderFactory,
                                 YahooResultReader<YahooQuoteRecord> reader,
                                 ItemProcessor<YahooQuoteRecord, StageYahooQuote> processor,
                                 ItemWriter<StageYahooQuote> writer) {
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

        ApplicationContext context
                = new ClassPathXmlApplicationContext("/spring/batch/jobs/yahoo-quote-job.xml");

        YahooQuoteApplication yahooHistoricalApplication
                = (YahooQuoteApplication) context.getBean("yahooQuoteApplication");

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

        UrlResource resource = YahooResourceHelper.getQuoteResource(symbols);
        reader.setResource(resource);

        TaskletStep step = stepBuilderFactory.get("yahooQuoteStep")
                .<YahooQuoteRecord, StageYahooQuote>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

        Job job = jobBuilderFactory.get("yahooQuoteLoad")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();

        JobExecution execution = jobLauncher.run(job, new JobParameters());
        LOG.info("Exit Status : " + execution.getStatus());
    }
}
