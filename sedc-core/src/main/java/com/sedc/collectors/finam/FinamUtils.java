package com.sedc.collectors.finam;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

public class FinamUtils {

    public static String buildUrl(String params[]) throws URISyntaxException {
        URIBuilder uri = new URIBuilder("http://export.finam.ru/out");
        uri.addParameter("market", params[0]);
        uri.addParameter("em", params[1]);
        uri.addParameter("code", params[2]);
        uri.addParameter("apply", params[3]);
        uri.addParameter("df", params[4]);
        uri.addParameter("mf", params[5]);
        uri.addParameter("yf", params[6]);
        uri.addParameter("from", params[7]);
        uri.addParameter("dt", params[8]);
        uri.addParameter("mt", params[9]);
        uri.addParameter("yt", params[10]);
        uri.addParameter("to", params[11]);
        uri.addParameter("p", params[12]);
        uri.addParameter("cn", params[13]);
        uri.addParameter("dtf", params[14]);
        uri.addParameter("tmf", params[15]);
        uri.addParameter("sep", params[16]);
        uri.addParameter("sep2", params[17]);
        uri.addParameter("datf", params[18]);
        return uri.build().toString();
    }
}
