package com.sedc.collectors.finam;

import com.sedc.Region;
import com.sedc.collectors.finam.model.FinamApiRecord;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
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
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.UrlResource;

import java.net.URISyntaxException;

/**
 * Created by SuperOleg on 01.03.2017.
 */
@Configurable
public class FinamApplication {

    private static final Logger LOG = Logger.getLogger(FinamApplication.class);

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

    public FinamApplication() {
    }

    public FinamApplication(JobLauncher jobLauncher, StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory, FlatFileItemReader reader, FinamApiProcessor processor, FlatFileItemWriter writer) {
        this.jobLauncher = jobLauncher;
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            LOG.info("USAGE: <period> <region>"); //TODO: make usage
            return;
        }

        ApplicationContext context =
                new ClassPathXmlApplicationContext("/spring/batch/jobs/finam-job.xml");
        FinamApplication finamApplication = (FinamApplication) context.getBean("finamApplication");
        try {
            finamApplication.run(args);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void run(String[] args) throws Exception {


        FinamPeriod period = FinamPeriod.getInstance(args[0]);
        Region region = Region.valueOf(args[1]);

        //TODO: make logic here, all below is just an example
        reader.setResource(new UrlResource("http://export.finam.ru/GAZP_170224_170301.txt?market=1&em=16842&code=GAZP&apply=0&df=24&mf=1&yf=2017&from=24.02.2017&dt=1&mt=2&yt=2017&to=01.03.2017&p=5&f=GAZP_170224_170301&e=.txt&cn=GAZP&dtf=1&tmf=1&MSOR=1&mstime=on&mstimever=1&sep=1&sep2=1&datf=1"));

        TaskletStep step = stepBuilderFactory.get("finamStep")
                .<FinamApiRecord, FinamApiRecord>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

        Job job = jobBuilderFactory.get("finamLoad")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();

        JobExecution execution = jobLauncher.run(job, new JobParameters());
        LOG.info("Exit Status : " + execution.getStatus());
    }

    private String getUrl(String args[]) throws URISyntaxException {
        URIBuilder uri = new URIBuilder("http://export.finam.ru");
        uri.addParameter("market", "1");
        uri.addParameter("em", "16842");
        return uri.build().getPath();
    }
}
