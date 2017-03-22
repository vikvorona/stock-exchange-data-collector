package com.sedc.collectors.yahoo.industry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/batch/jobs/yahoo-industry-load-hibernate-job-test.xml")
public class YahooIndustryHibernateTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private SessionFactory sessionFactory;

    @Test(timeout = 5000L)
    public void test() throws Exception {
        JobExecution execution = jobLauncher.run(job, new JobParameters());

        BatchStatus status = execution.getStatus();
        Assert.assertTrue(BatchStatus.COMPLETED.equals(status));

        List<Throwable> allFailureExceptions = execution.getAllFailureExceptions();
        if (!allFailureExceptions.isEmpty()){
            for (Throwable t : allFailureExceptions) {
                System.err.println(t);
            }
        }
        Assert.assertTrue(allFailureExceptions.isEmpty());

        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM INDUSTRY").uniqueResult();
        session.close();
        Assert.assertTrue(count.intValue() == 215);
    }
}
