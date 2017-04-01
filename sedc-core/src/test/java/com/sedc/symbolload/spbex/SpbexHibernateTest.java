package com.sedc.symbolload.spbex;

import com.sedc.core.model.Exchange;
import com.sedc.core.model.Symbol;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/batch/jobs/symbol-load-spbex-job-test.xml")
public class SpbexHibernateTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void setUp() {
        Session session = sessionFactory.openSession();
        Number count = (Number) session.createCriteria(Symbol.class).setProjection(Projections.rowCount()).uniqueResult();
        Assert.assertTrue("Some symbols already exist in database: " + count.longValue(), count.longValue() == 0L);

        count = (Number) session.createCriteria(Exchange.class)
                .add(Restrictions.eq("name", "MICEX"))
                .setProjection(Projections.rowCount())
                .uniqueResult();
        Assert.assertTrue("No MICEX exchange in database", count.longValue() == 1L);
        session.close();
    }

    @Test
    public void test() throws Exception {
        JobExecution execution = jobLauncherTestUtils.launchJob();

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
        Number count = (Number) session.createCriteria(Symbol.class).setProjection(Projections.rowCount()).uniqueResult();
        session.close();
        Assert.assertTrue("Symbols count mismatch: " + count.longValue(), count.longValue() == 539L);
    }
}
