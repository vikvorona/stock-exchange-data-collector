package com.sedc.collectors.yahoo.historical;

import com.sedc.core.ListResourceItemReader;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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

        return jobLauncherTestUtils.launchStep("loadToStage");
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
//        JobExecution jobExecution = launchStepFor("" +
//                "<quote Symbol=\"YHOO\">" +
//                "<AverageDailyVolume>6981940</AverageDailyVolume>\n" +
//                "<Change>+0.02</Change>" +
//                "<Days_Low>46.16</DaysLow>" +
//                "<DaysHigh>46.85</DaysHigh>" +
//                "<YearLow>35.05</YearLow>" +
//                "<YearHigh>47.19</YearHigh>" +
//                "<MarketCapitalization>44.41B</MarketCapitalization>" +
//                "<LastTradePriceOnly>46.43</LastTradePriceOnly>" +
//                "<DaysRange>46.16 - 46.85</DaysRange>" +
//                "<Name>Yahoo! Inc.</Name>" +
//                "<Volume>7675378</Volume>" +
//                "<StockExchange>NMS</StockExchange>" +
//                "</quote>");
        Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM STAGE_YAHOO_HISTORICAL WHERE SYMBOL ='YHOO' AND DATE = '2016-09-01' AND OPEN = 42.779999 AND HIGH = 43.099998 AND LOW = 42.720001 AND CLOSE = 42.93 AND VOLUME = 5575300").uniqueResult();
        // TODO: Rounded values
        // TODO: Sym_Id is Null
        Query q = session.createSQLQuery("DELETE FROM STAGE_YAHOO_HISTORICAL WHERE Volume =  5575300");
        q.executeUpdate();
        session.close();
        Assert.assertTrue(count.intValue() == 1);
    }

    @Test(expected = AssertionError.class)
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
        Query q = session.createSQLQuery("DELETE FROM STAGE_YAHOO_HISTORICAL WHERE SYMBOL = 'YHO'");
        q.executeUpdate();
        session.close();
        Assert.assertEquals("Wrong SYMBOL, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test(expected = AssertionError.class)
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
        Session session = sessionFactory.openSession();
        Query q = session.createSQLQuery("DELETE FROM STAGE_YAHOO_HISTORICAL WHERE SYMBOL = 'YHOO'");
        q.executeUpdate();
        session.close();
        Assert.assertEquals("Wrong type of field OPEN, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test(expected = AssertionError.class)
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
        Session session = sessionFactory.openSession();
        Query q = session.createSQLQuery("DELETE FROM STAGE_YAHOO_HISTORICAL WHERE VOLUME = 5575300");
        q.executeUpdate();
        session.close();
        Assert.assertEquals("Wrong DATE, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test(expected = AssertionError.class)
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
        Session session = sessionFactory.openSession();
        Query q = session.createSQLQuery("DELETE FROM STAGE_YAHOO_HISTORICAL WHERE VOLUME = 5575300");
        q.executeUpdate();
        session.close();
        Assert.assertEquals("No HIGH, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test(expected = AssertionError.class)
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
        Session session = sessionFactory.openSession();
        Query q = session.createSQLQuery("DELETE FROM STAGE_FINAM_HISTORICAL WHERE VOLUME = 5575300");
        q.executeUpdate();
        session.close();
        Assert.assertEquals("Wrong PER, should not pass", ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }
}
