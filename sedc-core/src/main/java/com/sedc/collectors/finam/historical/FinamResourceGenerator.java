package com.sedc.collectors.finam.historical;

import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.UrlResource;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class FinamResourceGenerator implements ItemWriter<String> {

    private static final Logger LOG = Logger.getLogger(FinamResourceGenerator.class);
    private static final String HOST_URL = "http://export.finam.ru";
    private static final String BASE_URL = "/table.csv?d=d&market=1&f=table&e=.csv&dtf=1&tmf=1&MSOR=1&sep=1&sep2=1&datf=1&";

    private List<UrlResource> urls;

    private String period;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private static URI buildUrl(String symbol, LocalDate dateFrom, LocalDate dateTo, String period) throws URISyntaxException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        URIBuilder uri = new URIBuilder(HOST_URL + BASE_URL);

        uri.addParameter("p", period);
        uri.addParameter("em", "16842"); //TODO load codes from [http://www.finam.ru/cache/icharts/icharts.js]

        uri.addParameter("df", String.valueOf(dateFrom.getDayOfMonth()));
        uri.addParameter("mf", String.valueOf(dateFrom.getMonthValue() - 1));
        uri.addParameter("yf", String.valueOf(dateFrom.getYear()));

        uri.addParameter("dt", String.valueOf(dateTo.getDayOfMonth()));
        uri.addParameter("mt", String.valueOf(dateTo.getMonthValue() - 1));
        uri.addParameter("yt", String.valueOf(dateTo.getYear()));

        uri.addParameter("cn", symbol);
        uri.addParameter("code", symbol);

        return uri.build();
    }

    public void write(List<? extends String> items) throws Exception {

        for (String symbol : items) {
            UrlResource resource = new UrlResource(buildUrl(symbol, dateFrom, dateTo, period));
            LOG.debug("Generated resource: " + resource);
            urls.add(resource);
        }
    }

    public void setUrls(List<UrlResource> urls) {
        this.urls = urls;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
