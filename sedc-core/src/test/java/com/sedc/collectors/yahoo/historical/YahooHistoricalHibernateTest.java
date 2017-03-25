package com.sedc.collectors.yahoo.historical;

import com.sedc.collectors.yahoo.util.YahooResourceHelper;
import com.sedc.collectors.yahoo.util.YahooResultReader;
import com.sedc.managers.SymbolManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/batch/jobs/yahoo-historical-load-hibernate-job-test.xml")
public class YahooHistoricalHibernateTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private YahooResultReader reader;

    @Autowired
    private SymbolManager symbolManager;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void setup() throws Exception {
        List<String> symbols = symbolManager.getStringSymbolsBySource("FINAM_HISTORY");

        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();

        UrlResource resource = YahooResourceHelper.getHistoricalDataResource(symbols, startDate, endDate);
        reader.setResource(resource);
    }

    @Test
    public void test() throws Exception {
        JobExecution execution = jobLauncher.run(job, new JobParameters());

        BatchStatus status = execution.getStatus();
        Assert.assertTrue(BatchStatus.COMPLETED.equals(status));

        List<Throwable> allFailureExceptions = execution.getAllFailureExceptions();
        if (!allFailureExceptions.isEmpty()) {
            for (Throwable t : allFailureExceptions) {
                System.err.println(t);
            }
        }
        Assert.assertTrue(allFailureExceptions.isEmpty());

        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM STAGE_YAHOO_HISTORICAL").uniqueResult();
        session.close();
        Assert.assertTrue(count.intValue() == 215);
    }
}
