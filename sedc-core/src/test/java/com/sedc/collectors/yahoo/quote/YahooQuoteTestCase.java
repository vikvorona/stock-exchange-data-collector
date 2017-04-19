package com.sedc.collectors.yahoo.quote;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/spring/batch/config/test-context.xml",
        "/spring/batch/jobs/yahoo/yahoo-quote-load-job.xml"})
public class YahooQuoteTestCase {

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
        JobExecution result =  jobLauncherTestUtils.launchStep("loadToStage");
        jobLauncherTestUtils.launchStep("filterStage");
        jobLauncherTestUtils.launchStep("linkSymbols");
        return result;
    }

    @Test
    public void testCase1() throws Exception {

        JobExecution jobExecution = launchStepFor("" +
                "<quote symbol=\"GAZP\">" +
                "<AverageDailyVolume>6599510</AverageDailyVolume>" +
                "<Change>0.2934</Change>" +
                "<DaysLow>46.9200</DaysLow>" +
                "<DaysHigh>47.2500</DaysHigh>" +
                "<YearLow>35.0500</YearLow>" +
                "<YearHigh>47.4150</YearHigh>" +
                "<MarketCapitalization>45.14B</MarketCapitalization>" +
                "<LastTradePriceOnly>47.1934</LastTradePriceOnly>" +
                "<DaysRange>46.9200 - 47.2500</DaysRange>" +
                "<Name>Yahoo! Inc.</Name>" +
                "<Volume>1875823</Volume>" +
                "<StockExchange>NMS</StockExchange>" +
                "</quote>");
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM STAGE_YAHOO_QUOTE WHERE "
                       + "symbol = 'GAZP' and Avg_Daily_Volume = 6599510 and Days_Low = 46.9200"
                       + "and Days_High = 47.2500 and Year_Low = 35.0500 and Name = 'Yahoo! Inc.' and Stock_Exchange = 'NMS' "
                       +"and Change = 0.2900 and Days_Range_from = 46.9200 and Days_Range_to = 47.2500"
                       +" and Sym_ID = (select s.Sym_ID from symbol s where s.name = 'GAZP') "
                       +"and Year_High = 47.4200 and Last_Trade_Price = 47.1900 and Market_Capitalization = 45140000000 and Volume = 1875823").uniqueResult();
//
        session.close();

        // TODO: SQL Query
        Assert.assertEquals("Count does not match", 1, count.intValue());
        Assert.assertEquals("Should pass good", ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());



    }
}
