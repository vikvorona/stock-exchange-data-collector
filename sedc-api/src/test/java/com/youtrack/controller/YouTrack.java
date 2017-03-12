package com.youtrack.controller;

import com.youtrack.view.Issue;
import com.youtrack.view.Project;
import org.springframework.http.*;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Based on YouTrack API https://www.jetbrains.com/help/youtrack/incloud/2017.1/General-REST-API.html
 */
public class YouTrack {

    private final String mBaseURL;
    private final String mUser;
    private final String mPassword;
    private final HttpHeaders mHeader = new HttpHeaders();

    public YouTrack(String pURL, String pUser, String pPassword) {
        String mURL = pURL.endsWith("/") ? pURL.substring(0, pURL.length() - 1) : pURL;
        mUser = pUser;
        mPassword = pPassword;
        mBaseURL = String.format("%s/rest", mURL);
        mHeader.setContentType(MediaType.APPLICATION_XML);
    }

    private HttpEntity<String> request(String pUrl, HttpMethod pMethod, MultiValueMap<String, Object> pData, MediaType pContentType) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(pUrl, pMethod, new HttpEntity<Object>(pData, mHeader), String.class);
    }

    public void login() throws HttpClientErrorException {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("login", mUser);
        map.add("password", mPassword);
        HttpEntity<String> response = restTemplate.postForEntity(mBaseURL + "/user/login?login={LOGIN}&password={PASSWORD}", map, String.class, mUser, mPassword);
        List<String> cookie = response.getHeaders().get("Set-Cookie");
        if (CollectionUtils.isEmpty(cookie)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        mHeader.put("Cookie", cookie);
    }

    public HttpEntity<String> createIssue(LinkedMultiValueMap<String, Object> pData) {
        return request(mBaseURL + "/issue", HttpMethod.PUT, pData, MediaType.APPLICATION_FORM_URLENCODED);
    }

    public HttpEntity<String> execute(String pLocation, LinkedMultiValueMap<String, Object> pData) {
        return request(pLocation + "/execute", HttpMethod.POST, pData, MediaType.APPLICATION_FORM_URLENCODED);
    }

    public Issue getIssuesInProject(String projectId) {
        return (Issue) getObject("/issue/byproject/{project}", Issue.class, projectId);
    }

    public Issue getIssue(String issueId) {
        return (Issue) getObject("/issue/{issue}", Issue.class, issueId);
    }

    public Project getProject(String projectId) {
        return (Project) getObject("/admin/project/{projectId}", Project.class, projectId);
    }

    private Object getObject(String urlRequest, Class responseType, Object... uriVariables) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections.singletonList(new Jaxb2RootElementHttpMessageConverter()));
        HttpMethod method = HttpMethod.GET;
        ResponseEntity<Object> entity = restTemplate.exchange(mBaseURL + urlRequest, method, new HttpEntity<>(mHeader), responseType, uriVariables);

//        entity.getStatusCode();
        return entity.getBody();
    }


}
