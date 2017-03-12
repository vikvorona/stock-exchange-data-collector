package com.youtrack;

import com.youtrack.controller.YouTrack;
import com.youtrack.view.Issue;
import com.youtrack.view.Project;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class YouTrackTest {

    private static final String URL = "https://sedc.myjetbrains.com/youtrack/";
    private static final String USER = "";
    private static final String PASSWORD = "";
    private static final String PROJECT = "SEDC";
    private static final YouTrack YOU_TRACK = new YouTrack(URL, USER, PASSWORD);

    @BeforeClass
    public static void before() {
        YOU_TRACK.login();
    }

    @Test
    public void testGetProject() throws Exception {
        Project project = YOU_TRACK.getProject(PROJECT);
        assertNotNull(project);
        System.out.println(project);
    }

    @Test
    public void testGetIssue() throws Exception {
        Issue issue = YOU_TRACK.getIssue("SEDC-18");
        assertNotNull(issue);
        System.out.println(issue);
    }

    @Test
    public void testSaveIssue() {
        //        IssueOld issue = new IssueOld(YOU_TRACK, PROJECT, "C'est un test avec un classe", "task");
    }
}
