package com.sedc.collectors.yahoo.historical;

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

/**
 * Created by Roman on 13.04.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/spring/batch/config/test-context.xml",
        "/spring/batch/jobs/yahoo/yahoo-historical-load-job.xml"})
public class YahooHistoricalTestCase {

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
                .addString("region", Region.EMEA.name())
                .addDate("launchTime", new Date())
                .toJobParameters();
        return jobLauncherTestUtils.launchJob(jobParameters);
    }

    @Test
    public void testCaseStagePositive() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"GAZP\">" +
                "<Date>2016-09-01</Date>" +
                "<Open>42.779999</Open>" +
                "<High>43.099998</High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        Assert.assertEquals("Should pass good", ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM STAGE_YAHOO_HISTORICAL WHERE "
                +"SYMBOL ='GAZP' AND DATE = '2016-09-01' AND OPEN = 42.78 AND HIGH = 43.1"
                +"AND LOW = 42.72 AND CLOSE = 42.93 AND VOLUME = 5575300" +
                " AND Sym_Id = (select s.sym_id from symbol s where s.name = :symbol)")
                .setString("symbol","GAZP")
                .uniqueResult();
        session.close();
        Assert.assertEquals("Count does not match", 1, count.intValue());
    }

    @Test
    public void testCaseStageNegativeWrongSymbol() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHO\">" +
                "<Date>2016-09-01</Date>" +
                "<Open>42.779999</Open>" +
                "<High>43.099998</High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        Session session = sessionFactory.openSession();
        Character flag = (Character) session.createSQLQuery("SELECT ACTIVE_FLAG FROM STAGE_YAHOO_HISTORICAL WHERE SYMBOL = :symbol")
                .addScalar("ACTIVE_FLAG", StandardBasicTypes.CHARACTER)
                .setString("symbol", "YHO")
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        Character c = 'N';
        Assert.assertEquals("Wrong SYMBOL, should not pass", c, flag);
    }

    @Test
    public void testCaseStageNegativeWrongData() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHOO\">" +
                "<Date>2016-09-01</Date>" +
                "<Open>aaa</Open>" +
                "<High>43.099998</High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        Session session = sessionFactory.openSession();
        Character flag = (Character) session.createSQLQuery("SELECT ACTIVE_FLAG FROM STAGE_YAHOO_HISTORICAL WHERE SYMBOL = :symbol")
                .addScalar("ACTIVE_FLAG", StandardBasicTypes.CHARACTER)
                .setString("symbol", "YHOO")
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        Character c = 'N';
        Assert.assertEquals("Wrong Data, should not pass", c, flag);
        //TODO: expected action_reason "Data is corrupted", actual "Symbol not found"
    }

    @Test
    public void testCaseStageNegativeWrongDate() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHOO\">" +
                "<Date>2016000901</Date>" +
                "<Open>42.779999</Open>" +
                "<High>43.099998</High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        Assert.assertEquals("Wrong DATE, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCaseStageNegativeEmptyField() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHOO\">" +
                "<Date>2016-09-01</Date>" +
                "<Open>42.779999</Open>" +
                "<High></High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        Session session = sessionFactory.openSession();
        Character flag = (Character) session.createSQLQuery("SELECT ACTIVE_FLAG FROM STAGE_YAHOO_HISTORICAL WHERE SYMBOL = :symbol")
                .addScalar("ACTIVE_FLAG", StandardBasicTypes.CHARACTER)
                .setString("symbol", "YHOO")
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        Character c = 'N';
        Assert.assertEquals("Empty field, should not pass", c, flag);
    }

    @Test
    public void testCaseSnapshotPositive() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"GAZP\">" +
                "<Date>2016-01-01</Date>" +
                "<Open>42.779999</Open>" +
                "<High>43.099998</High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        jobLauncherTestUtils.launchStep("saveToSnapshot");
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("" +
                "SELECT count(1) FROM SNAPSHOT_HISTORICAL WHERE " +
                " DATE = '2016-01-01' AND OPEN = 42.78 AND HIGH = 43.1" +
                " AND LOW = 42.72 AND CLOSE = 42.93 AND VOLUME = 5575300" +
                " AND Sym_Id = (select s.sym_id from symbol s where s.name = :symbol)" +
                " AND scei_id = :scei")
                .setString("symbol", "GAZP")
                .setBigDecimal("scei", sourceCenterEngineInstance.getId())
                .uniqueResult();
        session.close();
        Assert.assertEquals("Count does not match", 1, count.intValue());
        Assert.assertEquals("should pass", ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCaseSnapshotNegative() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHO\">" +
                "<Date>2016-02-02</Date>" +
                "<Open>42.779999</Open>" +
                "<High>43.099998</High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        jobLauncherTestUtils.launchStep("saveToSnapshot");
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM SNAPSHOT_HISTORICAL WHERE "
                + "DATE = '2016-02-02' AND OPEN = 42.78 AND HIGH = 43.1"
                + "AND LOW = 42.72 AND CLOSE = 42.93 AND VOLUME = 5575300").uniqueResult();
        session.close();
        Assert.assertEquals("Count does not match", 0, count.intValue());
    }
}
