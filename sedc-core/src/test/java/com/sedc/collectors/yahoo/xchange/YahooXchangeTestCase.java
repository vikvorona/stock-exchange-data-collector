package com.sedc.collectors.yahoo.xchange;

import com.sedc.Region;
import com.sedc.core.ListResourceItemReader;
import com.sedc.core.model.SourceCenterEngineInstance;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/spring/batch/config/test-context.xml",
        "/spring/batch/jobs/yahoo/yahoo-xchange-load-job.xml"})
public class YahooXchangeTestCase {

    @Rule
    public TestName name = new TestName();

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ListResourceItemReader multiResourceReader;

    @Autowired
    private SourceCenterEngineInstance sourceCenterEngineInstance;

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

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("region", Region.FOREX.name())
                .addDate("launchTime", new Date())
                .toJobParameters();
        return jobLauncherTestUtils.launchJob(jobParameters);
    }

    @Test
    public void testCaseStagePositive() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<rate id=\"EURUSD\">" +
                "<Name>EUR/USD</Name>" +
                "<Rate>1.7</Rate>" +
                "<Date>5/17/2017</Date>" +
                "<Time>5:57pm</Time>" +
                "<Ask>1.062</Ask>" +
                "<Bid>1.0656</Bid>" +
                "</rate>");
        Assert.assertEquals("Should pass good", ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());

        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM " +
                "STAGE_YAHOO_FXRATE WHERE Name='EUR/USD' " +
                "and Rate=1.7 and Date = '2017-05-17' and Time= '17:57:00' " +
                "and Ask= 1.062 and Bid = 1.0656 " +
                "and Sym_Id = (select s.sym_id from symbol s where s.name = 'EURUSD')").uniqueResult();
        session.close();
        Assert.assertEquals("Count does not match", 1, count.intValue());
    }

    @Test
    public void testCaseStageNegativeWrongId() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<rate id=\"UUU\">" +
                "<Name>EUR/USD</Name>" +
                "<Rate>1.7</Rate>" +
                "<Date>5/17/2017</Date>" +
                "<Time>5:57pm</Time>" +
                "<Ask>1.062</Ask>" +
                "<Bid>1.06</Bid>" +
                "</rate>");
        Session session = sessionFactory.openSession();
        Character flag = (Character) session.createSQLQuery("SELECT ACTIVE_FLAG FROM STAGE_YAHOO_FXRATE WHERE ID = :id")
                .addScalar("ACTIVE_FLAG", StandardBasicTypes.CHARACTER)
                .setString("id", "UUU")
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        Character c = 'N';
        Assert.assertEquals("Wrong Id, should not pass", c, flag);
    }

    @Test
    public void testCaseStageNegativeWrongData() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<rate id=\"EURUSD\">" +
                "<Name>EUR/USD</Name>" +
                "<Rate>aa</Rate>" +
                "<Date>5/17/2017</Date>" +
                "<Time>5:57pm</Time>" +
                "<Ask>1.062</Ask>" +
                "<Bid>1.06</Bid>" +
                "</rate>");
        Session session = sessionFactory.openSession();
        Character flag = (Character) session.createSQLQuery("SELECT ACTIVE_FLAG FROM STAGE_YAHOO_FXRATE WHERE ID = :id")
                .addScalar("ACTIVE_FLAG", StandardBasicTypes.CHARACTER)
                .setString("id", "EURUSD")
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        Character c = 'N';
        Assert.assertEquals("Wrong Data, should not pass", c, flag);
    }

    @Test
    public void testCaseStageNegativeWrongTime() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<rate id=\"EURUSD\">" +
                "<Name>EUR/USD</Name>" +
                "<Rate>1.7</Rate>" +
                "<Date>5/17/2017</Date>" +
                "<Time>enter</Time>" +
                "<Ask>1.062</Ask>" +
                "<Bid>1.0656</Bid>" +
                "</rate>");
        Assert.assertEquals("Wrong TIME, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCaseStageNegativeWrongDate() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<rate id=\"EURUSD\">" +
                "<Name>EUR/USD</Name>" +
                "<Rate>1.7</Rate>" +
                "<Date>5517/2017</Date>" +
                "<Time>enter</Time>" +
                "<Ask>1.062</Ask>" +
                "<Bid>1.0656</Bid>" +
                "</rate>");
        Assert.assertEquals("Wrong DATE, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCaseStageNegativeEmptyField() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<rate id=\"EURUSD\">" +
                "<Name></Name>" +
                "<Rate>1.7</Rate>" +
                "<Date>5/17/2017</Date>" +
                "<Time>enter</Time>" +
                "<Ask>1.062</Ask>" +
                "<Bid>1.0656</Bid>" +
                "</rate>");
        Assert.assertEquals("Empty field, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCaseSnapshotPositive() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<rate id=\"EURUSD\">" +
                "<Name>EUR/USD</Name>" +
                "<Rate>1.7</Rate>" +
                "<Date>5/17/2017</Date>" +
                "<Time>5:57pm</Time>" +
                "<Ask>1.062</Ask>" +
                "<Bid>1.0656</Bid>" +
                "</rate>");
        jobLauncherTestUtils.launchStep("saveToSnapshot");
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM SNAPSHOT_FXRATE WHERE " +
                "Name='EUR/USD' and Rate=1.7 and Date = '2017-05-17' and " +
                "Time= '17:57:00' and Ask= 1.062 and Bid = 1.0656").uniqueResult();
        session.createSQLQuery("DELETE FROM SNAPSHOT_FXRATE WHERE Bid=1.0656").executeUpdate();
        session.close();
        //TODO: saving to snapshot doesn't work
        Assert.assertEquals("Count does not match", 1, count.intValue());
        Assert.assertEquals("should pass", ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }
}
