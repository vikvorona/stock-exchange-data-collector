package com.sedc.collectors.yahoo.historical;

import com.sedc.core.ListResourceItemReader;
import org.hibernate.Query;
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

    private List<UrlResource> resources = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
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
        jobLauncherTestUtils.launchStep("filterStage");
        jobLauncherTestUtils.launchStep("linkSymbols");
        return result;
    }

    @Test
    public void testCase1() throws Exception {

        //launchStepFor("EURUSD,EUR/USD,1.0656,4/4/2017,1:36pm,1.0657,1.0656");
        //launchStepFor("GAZP,69,20170224,101500,136.5100000,136.7000000,135.7000000,136.0700000,1063690");
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHOO\">" +
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
                +"SYMBOL ='YHOO' AND DATE = '2016-09-01' AND OPEN = 42.78 AND HIGH = 43.1"
                +"AND LOW = 42.72 AND CLOSE = 42.93 AND VOLUME = 5575300").uniqueResult();
        // TODO: Rounded values
        // TODO: Sym_Id is Null
       // Query q = session.createSQLQuery("DELETE FROM STAGE_YAHOO_HISTORICAL WHERE Volume =  5575300");
        //q.executeUpdate();
        session.close();
        Assert.assertEquals("Count does not match", 1, count.intValue());
    }

    @Test
    public void testCase2() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHO\">" +
                "<Date>2016-09-01</Date>" +
                "<Open>42.779999</Open>" +
                "<High>43.099998</High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        // TODO: Wrong SYMBOL doesn't throws an Exception
        Session session = sessionFactory.openSession();
        Character flag = (Character) session.createSQLQuery("SELECT ACTIVE_FLAG FROM STAGE_YAHOO_HISTORICAL WHERE SYMBOL = :symbol")
                .addScalar("ACTIVE_FLAG", StandardBasicTypes.CHARACTER)
                .setString("symbol", "YHO")
                .setMaxResults(1)
                .uniqueResult();
       /// Query q = session.createSQLQuery("DELETE FROM STAGE_YAHOO_HISTORICAL WHERE SYMBOL = 'YHO'");
       // q.executeUpdate();
        session.close();
        Assert.assertEquals("Wrong SYMBOL, should not pass", "N", flag);
        Assert.assertEquals("Wrong SYMBOL, should not pass", ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCase3() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHOO\">" +
                "<Date>2016-09-01</Date>" +
                "<Open>aaa</Open>" +
                "<High>43.099998</High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        //Session session = sessionFactory.openSession();
        //Query q = session.createSQLQuery("DELETE FROM STAGE_YAHOO_HISTORICAL WHERE SYMBOL = 'YHOO'");
        //q.executeUpdate();
        //session.close();
        Assert.assertEquals("Wrong type of field OPEN, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCase4() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHOO\">" +
                "<Date>2016000901</Date>" +
                "<Open>42.779999</Open>" +
                "<High>43.099998</High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        // TODO: Date format
        //Session session = sessionFactory.openSession();
        //Query q = session.createSQLQuery("DELETE FROM STAGE_YAHOO_HISTORICAL WHERE VOLUME = 5575300");
        //q.executeUpdate();
        //session.close();
        Assert.assertEquals("Wrong DATE, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCase5() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHOO\">" +
                "<Date>2016-09-01</Date>" +
                "<Open>42.779999</Open>" +
                "<High></High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        // TODO: Date format
        //Session session = sessionFactory.openSession();
        //Query q = session.createSQLQuery("DELETE FROM STAGE_YAHOO_HISTORICAL WHERE VOLUME = 5575300");
        //q.executeUpdate();
        //session.close();
        Assert.assertEquals("No HIGH, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Ignore("Period is parsed before launch")
    @Test
    public void testCasePeriod() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHOO\">" +
                "<Per>Z</Per>" + 
                "<Date>2016-09-01</Date>" +
                "<Open>42.779999</Open>" +
                "<High>43.099998</High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        // TODO: Wrong PER doesn't throws an Exception
        //Session session = sessionFactory.openSession();
        //Query q = session.createSQLQuery("DELETE FROM STAGE_FINAM_HISTORICAL WHERE VOLUME = 5575300");
        //q.executeUpdate();
        //session.close();
        Assert.assertEquals("Wrong PER, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Ignore("there are not load to snapshot yet")
    @Test
    public void testCaseSnapshot() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote Symbol=\"YHOO\">" +
                "<Date>2016-09-01</Date>" +
                "<Open>42.779999</Open>" +
                "<High>43.099998</High>" +
                "<Low>42.720001</Low>" +
                "<Close>42.93</Close>" +
                "<Volume>5575300</Volume>" +
                "</quote>");
        jobLauncherTestUtils.launchStep("loadToSnapshot");
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM SNAPSHOT_HISTORICAL WHERE "
                +"SYMBOL ='YHOO' AND DATE = '2016-09-01' AND OPEN = 42.78 AND HIGH = 43.1"
                +"AND LOW = 42.72 AND CLOSE = 42.93 AND VOLUME = 5575300").uniqueResult();
        session.createSQLQuery("DELETE FROM SNAPSHOT_HISTORICAL WHERE VOLUME = 1063690").executeUpdate();
        session.close();
        Assert.assertEquals("Count does not match", 1, count.intValue());
        Assert.assertEquals("should pass", ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }
}
