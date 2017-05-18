package com.sedc.collectors.finam.historical;

import com.sedc.core.ListResourceItemReader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/spring/batch/config/test-context.xml",
        "/spring/batch/jobs/finam/finam-historical-load-job.xml"})
public class FinamTestCase {

    @Rule
    public TestName name = new TestName();

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ListResourceItemReader multiResourceReader;
    private List<UrlResource> resources = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        resources.clear();
        multiResourceReader.setResources(resources);
    }

    private JobExecution launchStepFor(String value) throws Exception {
        // write string to temp file
        File f = File.createTempFile(name.getMethodName(), null);
        FileWriter fw = new FileWriter(f);
        fw.write(value);
        fw.close();
        // put temp file in resources
        resources.add(new UrlResource(f.toURI()));

        jobLauncherTestUtils.launchStep("clearStage");
        JobExecution result = jobLauncherTestUtils.launchStep("loadToStage");
        jobLauncherTestUtils.launchStep("linkSymbols");
        jobLauncherTestUtils.launchStep("filterStage");
        return result;
    }

    @Test
    public void testCaseStagePositive() throws Exception {
        JobExecution jobExecution = launchStepFor("GAZP,60,20170224,101500,136.5100000,136.7000000,135.7000000,136.0700000,1063690");
        Assert.assertEquals("Should pass good", ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM STAGE_FINAM_HISTORICAL WHERE "
                + "SYMBOL = 'GAZP' AND VOLUME = 1063690 AND OPEN = 136.51 AND HIGH = 136.7 AND LOW = 135.7 AND CLOSE = 136.07"
                + "AND DATE = '2017-02-24' AND TIME = '10:15:00' AND SYM_ID = (select s.sym_id from symbol s where s.name = 'GAZP')").uniqueResult();
        session.close();
        Assert.assertEquals("Count does not match", 1, count.intValue());
    }


    @Test
    public void testCaseStageNegativeWrongSymbol() throws Exception {
        JobExecution jobExecution = launchStepFor("TEST,60,20170224,101500,136.5100000,136.7000000,135.7000000,136.0700000,1063690");
        Session session = sessionFactory.openSession();
        Character flag = (Character) session.createSQLQuery("SELECT ACTIVE_FLAG FROM STAGE_FINAM_HISTORICAL WHERE SYMBOL = :symbol")
                .addScalar("ACTIVE_FLAG", StandardBasicTypes.CHARACTER)
                .setString("symbol", "TEST")
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        Character c = 'N';
        Assert.assertEquals("Wrong SYMBOL, should not pass", c, flag);
    }

    @Test
    public void testCaseStageNegativeWrongData() throws Exception {
        JobExecution jobExecution = launchStepFor("GAZP,60,20170224,101500,aa,136.7000000,135.7000000,136.0700000,1063690");
        Assert.assertEquals("Wrong OPEN, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCaseStageNegativeWrongDate() throws Exception {
        JobExecution jobExecution = launchStepFor("GAZP,60,201702224,101500,136.5100000,136.7000000,135.7000000,136.0700000,1063690");
        Assert.assertEquals("Wrong DATE, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCaseStageNegativeEmptyField() throws Exception {
        JobExecution jobExecution = launchStepFor("GAZP,60,201000365,101500,136.5100000,136.7000000,1063690");
        Assert.assertEquals("No HIGH, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCaseStageNegativeWrongPeriod() throws Exception {
        JobExecution jobExecution = launchStepFor("GAZP,Z,20170224,101500,136.5100000,136.7000000,135.7000000,136.0700000,1063690");
        Assert.assertEquals("Wrong PER, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCaseSnapshotPositive() throws Exception {
        JobExecution jobExecution = launchStepFor("GAZP,60,20170924,101500,136.5100000,136.7000000,135.7000000,136.0700000,1063690");
        jobLauncherTestUtils.launchStep("loadToSnapshot");
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM SNAPSHOT_HISTORICAL WHERE "
                + "VOLUME = 1063690 AND OPEN = 136.51 AND HIGH = 136.7 AND LOW = 135.7 AND CLOSE = 136.07 "
                + "AND DATE = '2017-09-24' AND TIME = '10:15:00' AND SYM_ID = (select s.sym_id from symbol s where s.name = 'GAZP') ").uniqueResult();
        session.createSQLQuery("DELETE FROM SNAPSHOT_HISTORICAL WHERE VOLUME = 1063690").executeUpdate();
        session.close();
        Assert.assertEquals("Count does not match", 1, count.intValue());
    }

    @Test
    public void testCaseSnapshotNegative() throws Exception {
        JobExecution jobExecution = launchStepFor("TEST,60,20170924,101500,136.5100000,136.7000000,135.7000000,136.0700000,1063690");
        jobLauncherTestUtils.launchStep("loadToSnapshot");
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM SNAPSHOT_HISTORICAL WHERE "
                + "VOLUME = 1063690 AND OPEN = 136.51 AND HIGH = 136.7 AND LOW = 135.7 AND CLOSE = 136.07 "
                + "AND DATE = '2017-09-24' AND TIME = '10:15:00'").uniqueResult();
        session.close();
        Assert.assertEquals("Count does not match", 0, count.intValue());
    }
}
