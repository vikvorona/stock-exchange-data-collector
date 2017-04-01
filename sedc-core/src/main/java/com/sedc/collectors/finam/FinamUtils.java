package com.sedc.collectors.finam;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class FinamUtils {

    private static final String BASE_URL = "http://export.finam.ru/out";
    private static final String DEFAULT_FLAG = "1";
    private static final String MARKET = DEFAULT_FLAG;
    private static final String TICKER_FLAG = DEFAULT_FLAG;
    private static final String SEPARATOR1 = DEFAULT_FLAG;
    private static final String SEPARATOR2 = DEFAULT_FLAG;
    private static final String DATE_FORMAT_FLAG = DEFAULT_FLAG;
    private static final String TIME_FORMAT_FLAG = DEFAULT_FLAG;

    public static String buildUrl(String symbol, Date dateFrom, Date dateTo, String period) throws URISyntaxException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        LocalDate localDateFrom = dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateTo = dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        URIBuilder uri = new URIBuilder(BASE_URL);
        uri.addParameter("market", MARKET);
        uri.addParameter("em", "16842");
        uri.addParameter("code", symbol);
        uri.addParameter("apply", "0");
        uri.addParameter("df", String.valueOf(localDateFrom.getDayOfMonth()));
        uri.addParameter("mf", String.valueOf(localDateFrom.getMonth()));
        uri.addParameter("yf", String.valueOf(localDateFrom.getYear()));
        uri.addParameter("from", dateFormat.format(dateFrom));
        uri.addParameter("dt", String.valueOf(localDateTo.getDayOfMonth()));
        uri.addParameter("mt", String.valueOf(localDateTo.getMonth()));
        uri.addParameter("yt", String.valueOf(localDateTo.getYear()));
        uri.addParameter("to", dateFormat.format(dateTo));
        uri.addParameter("p", period);
        uri.addParameter("cn", symbol);
        uri.addParameter("dtf", DATE_FORMAT_FLAG);
        uri.addParameter("tmf", TIME_FORMAT_FLAG);
        uri.addParameter("sep", SEPARATOR1);
        uri.addParameter("sep2", SEPARATOR2);
        uri.addParameter("datf", TICKER_FLAG);
        return uri.build().toString();
    }
}
