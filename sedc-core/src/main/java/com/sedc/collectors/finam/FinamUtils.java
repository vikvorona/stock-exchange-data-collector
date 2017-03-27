package com.sedc.collectors.finam;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FinamUtils {

    private static final String BASE_URL = "http://export.finam.ru/out";
    private static final String MARKET = "1";
    private static final String TICKER_FLAG = "1";
    private static final String SEPARATOR1 = "1";
    private static final String SEPARATOR2 = "1";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static String buildUrl(String symbol, Date dateFrom, Date dateTo) throws URISyntaxException {
        URIBuilder uri = new URIBuilder(BASE_URL);
        uri.addParameter("market", MARKET);
        uri.addParameter("em", "16842");
        uri.addParameter("code", symbol);
        uri.addParameter("apply", "0");
        uri.addParameter("df", getDay(dateFrom));
        uri.addParameter("mf", getMonth(dateFrom));
        uri.addParameter("yf", getYear(dateFrom));
        uri.addParameter("from", dateFormat.format(dateFrom));
        uri.addParameter("dt", getDay(dateTo));
        uri.addParameter("mt", getMonth(dateTo));
        uri.addParameter("yt", getYear(dateTo));
        uri.addParameter("to", dateFormat.format(dateTo));
        uri.addParameter("p", "5");
        uri.addParameter("cn", symbol);
        uri.addParameter("dtf", "1");
        uri.addParameter("tmf", "1");
        uri.addParameter("sep", SEPARATOR1);
        uri.addParameter("sep2", SEPARATOR2);
        uri.addParameter("datf", TICKER_FLAG);
        return uri.build().toString();
    }

    private static String getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    private static String getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return String.valueOf(month);
    }

    private static String getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(day);
    }
}
