package com.sedc.collectors.yahoo.xchange;

import com.sedc.core.ListResourceItemReader;
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
import java.util.ArrayList;
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
        JobExecution jobExecution = launchStepFor("" +
                "<rate id=\"TEST\">" +
                "<Name>TEST</Name>" +
                "<Rate>1.7</Rate>" +
                "<Date>5/17/2017</Date>" +
                "<Time>5:57pm</Time>" +
                "<Ask>1.062</Ask>" +
                "<Bid>1.0656</Bid>" +
                "</rate>");;
        Assert.assertEquals("Should pass good", ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());

        Session session = sessionFactory.openSession();
        // TODO: SQL Query
       /* Query q = session.createSQLQuery("DELETE FROM STAGE_YAHOO_FXRATE WHERE ID = 'TEST'");
        q.executeUpdate();*/
        session.close();
    }
}
