package com.sedc.collectors.yahoo.util;

import org.springframework.core.io.UrlResource;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class YahooResourceHelper {

    private static final String API_ROOT = "http://query.yahooapis.com/v1/public/yql";
    private static final String YQL_COMMUNITY_TABLES = "store://datatables.org/alltableswithkeys";

    private static final String YQL_HISTORICAL = "select * from yahoo.finance.historicaldata where %1$s and %2$s";
    private static final String YQL_QUOTE = "select * from yahoo.finance.quote where %1$s";
    private static final String YQL_XCHANGE = "select * from yahoo.finance.xchange where %1$s";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("\"yyyy-MM-dd\"");

    public static UrlResource getHistoricalDataResource(List<String> symbols, LocalDate startDate, LocalDate endDate)
            throws MalformedURLException {
        String query = String.format(YQL_HISTORICAL, getSymbolCriteria(symbols, "symbol"), getDateCriteria(startDate, endDate));
        return getYQLResource(query);
    }

    public static UrlResource getQuoteResource(List<String> symbols)
            throws MalformedURLException {
        String query = String.format(YQL_QUOTE, getSymbolCriteria(symbols, "symbol"));
        return getYQLResource(query);
    }

    public static UrlResource getXchangeResource(List<String> pairs)
            throws MalformedURLException {
        String query = String.format(YQL_XCHANGE, getSymbolCriteria(pairs, "pair"));
        return getYQLResource(query);
    }

    public static UrlResource getYQLResource(String query) throws MalformedURLException {
        String uriString = UriComponentsBuilder.fromHttpUrl(API_ROOT)
                .queryParam("q", query)
                .queryParam("diagnostics", false)
                .queryParam("format", "xml")
                .queryParam("env", YQL_COMMUNITY_TABLES)
                .toUriString();
        return new UrlResource(uriString);
    }

    private static String getSymbolCriteria(List<String> symbols, final String key) {
        String symbolCriteria = symbols.stream()
                .map(s -> '"' + s + '"')
                .collect(Collectors.joining(","));
        return key + " in (" + symbolCriteria + ")";
    }

    private static String getDateCriteria(LocalDate startDate, LocalDate endDate) {
        return "startDate = " + startDate.format(formatter) + " and endDate = " + endDate.format(formatter);
    }
}
