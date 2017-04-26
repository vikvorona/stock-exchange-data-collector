package com.sedc.collectors.yahoo.quote;

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
        JobExecution result = jobLauncherTestUtils.launchStep("loadToStage");
        jobLauncherTestUtils.launchStep("filterStage");
        jobLauncherTestUtils.launchStep("linkSymbols");
        jobLauncherTestUtils.launchStep("filterBySymbol");
        return result;
    }

    @Test
    public void testCaseStagePositive() throws Exception {

        JobExecution jobExecution = launchStepFor("" +
                "<quote symbol=\"GAZP\">" +
                "<AverageDailyVolume>6981940</AverageDailyVolume>" +
                "<Change>0.02</Change>" +
                "<DaysLow>46.85</DaysLow>" +
                "<DaysHigh>47.25</DaysHigh>" +
                "<YearLow>35.0500</YearLow>" +
                "<YearHigh>47.19</YearHigh>" +
                "<MarketCapitalization>44.41B</MarketCapitalization>" +
                "<LastTradePriceOnly>46.43</LastTradePriceOnly>" +
                "<DaysRange>46.16 - 47.85</DaysRange>" +
                "<Name>Yahoo! Inc.</Name>" +
                "<Volume>7675378</Volume>" +
                "<StockExchange>NMS</StockExchange>" +
                "</quote>");
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM STAGE_YAHOO_QUOTE WHERE "
                + "symbol = 'GAZP' and Avg_Daily_Volume = 6981940 and Days_Low = 46.85"
                + "and Days_High = 47.25 and Year_Low = 35.0500 and Name = 'Yahoo! Inc.' and Stock_Exchange = 'NMS' "
                + "and Change = 0.02 and Days_Range_from = 46.16 and Days_Range_to = 47.85"
                + " and Sym_ID = (select s.Sym_ID from symbol s where s.name = 'GAZP') "
                + "and Year_High = 47.1900 and Last_Trade_Price = 46.43 and Market_Capitalization = 44410000000 and Volume = 7675378").uniqueResult();
        session.close();
        Assert.assertEquals("Count does not match", 1, count.intValue());
        Assert.assertEquals("Should pass good", ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void testCaseStageNegativeWrongSymbol() throws Exception {

        JobExecution jobExecution = launchStepFor("" +
                "<quote symbol=\"TEST\">" +
                "<AverageDailyVolume>6981940</AverageDailyVolume>" +
                "<Change>0.02</Change>" +
                "<DaysLow>46.85</DaysLow>" +
                "<DaysHigh>47.25</DaysHigh>" +
                "<YearLow>35.0500</YearLow>" +
                "<YearHigh>47.19</YearHigh>" +
                "<MarketCapitalization>44.41B</MarketCapitalization>" +
                "<LastTradePriceOnly>46.43</LastTradePriceOnly>" +
                "<DaysRange>46.16 - 47.85</DaysRange>" +
                "<Name>Yahoo! Inc.</Name>" +
                "<Volume>7675378</Volume>" +
                "<StockExchange>NMS</StockExchange>" +
                "</quote>");
        Session session = sessionFactory.openSession();
        Character flag = (Character) session.createSQLQuery("SELECT ACTIVE_FLAG FROM STAGE_YAHOO_QUOTE WHERE SYMBOL = :symbol")
                .addScalar("ACTIVE_FLAG", StandardBasicTypes.CHARACTER)
                .setString("symbol", "TEST")
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        Character c = 'N';
        Assert.assertEquals("Wrong SYMBOL", c, flag);
    }

    @Test
    public void testCaseStageNegativeWrongData() throws Exception {

        JobExecution jobExecution = launchStepFor("" +
                "<quote symbol=\"GAZP\">" +
                "<AverageDailyVolume>6981940</AverageDailyVolume>" +
                "<Change>0.02</Change>" +
                "<DaysLow>46.85</DaysLow>" +
                "<DaysHigh>47.25</DaysHigh>" +
                "<YearLow>35.0500</YearLow>" +
                "<YearHigh>47.19</YearHigh>" +
                "<MarketCapitalization>44.41B</MarketCapitalization>" +
                "<LastTradePriceOnly>46.43</LastTradePriceOnly>" +
                "<DaysRange>46.16 - 47.85</DaysRange>" +
                "<Name>Yahoo! Inc.</Name>" +
                "<Volume>aa</Volume>" +
                "<StockExchange>NMS</StockExchange>" +
                "</quote>");
        Session session = sessionFactory.openSession();
        Character flag = (Character) session.createSQLQuery("SELECT ACTIVE_FLAG FROM STAGE_YAHOO_QUOTE WHERE SYMBOL = :symbol")
                .addScalar("ACTIVE_FLAG", StandardBasicTypes.CHARACTER)
                .setString("symbol", "GAZP")
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        Character c = 'N';
        Assert.assertEquals("Wrong Data", c, flag);
    }

    @Test
    public void testCaseStageNegativeEmptyField() throws Exception {

        JobExecution jobExecution = launchStepFor("" +
                "<quote symbol=\"GAZP\">" +
                "<AverageDailyVolume>6981940</AverageDailyVolume>" +
                "<Change>0.02</Change>" +
                "<DaysLow>46.85</DaysLow>" +
                "<DaysHigh>47.25</DaysHigh>" +
                "<YearLow>35.0500</YearLow>" +
                "<YearHigh>47.19</YearHigh>" +
                "<MarketCapitalization>44.41B</MarketCapitalization>" +
                "<LastTradePriceOnly>46.43</LastTradePriceOnly>" +
                "<DaysRange>46.16 - 47.85</DaysRange>" +
                "<Name></Name>" +
                "<Volume>7675378</Volume>" +
                "<StockExchange>NMS</StockExchange>" +
                "</quote>");
        Session session = sessionFactory.openSession();
        Character flag = (Character) session.createSQLQuery("SELECT ACTIVE_FLAG FROM STAGE_YAHOO_QUOTE WHERE SYMBOL = :symbol")
                .addScalar("ACTIVE_FLAG", StandardBasicTypes.CHARACTER)
                .setString("symbol", "GAZP")
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        Character c = 'N';
        Assert.assertEquals("Empty field", c, flag);
    }

    @Test
    public void testCaseSnapshotPositive() throws Exception {
        JobExecution jobExecution = launchStepFor("" +
                "<quote symbol=\"GAZP\">" +
                "<AverageDailyVolume>6981940</AverageDailyVolume>" +
                "<Change>0.02</Change>" +
                "<DaysLow>46.85</DaysLow>" +
                "<DaysHigh>47.25</DaysHigh>" +
                "<YearLow>35.0500</YearLow>" +
                "<YearHigh>47.19</YearHigh>" +
                "<MarketCapitalization>44.41B</MarketCapitalization>" +
                "<LastTradePriceOnly>46.43</LastTradePriceOnly>" +
                "<DaysRange>46.16 - 47.85</DaysRange>" +
                "<Name>Yahoo! Inc.</Name>" +
                "<Volume>7675378</Volume>" +
                "<StockExchange>NMS</StockExchange>" +
                "</quote>");
        jobLauncherTestUtils.launchStep("saveToSnapshot");
        Session session = sessionFactory.openSession();
        BigInteger count = (BigInteger) session.createSQLQuery("SELECT count(1) FROM SNAPSHOT_QUOTE WHERE "
                + "Avg_Daily_Volume = 6981940 and Days_Low = 46.85"
                + "and Days_High = 47.25 and Year_Low = 35.0500 and Name = 'Yahoo! Inc.' and Stock_Exchange = 'NMS' "
                + "and Change = 0.02 and Days_Range_from = 46.16 and Days_Range_to = 47.85 "
                + "and Sym_ID = (select s.Sym_ID from symbol s where s.name = 'GAZP') "
                + "and Year_High = 47.1900 and Last_Trade_Price = 46.43 " +
                "and Market_Capitalization = 44410000000 and Volume = 7675378").uniqueResult();
        session.createSQLQuery("DELETE FROM SNAPSHOT_QUOTE WHERE VOLUME = 7675378").executeUpdate();
        session.close();
        //TODO: Doesn't save to snapshot
        Assert.assertEquals("Count does not match", 1, count.intValue());
        Assert.assertEquals("should pass", ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }
}
