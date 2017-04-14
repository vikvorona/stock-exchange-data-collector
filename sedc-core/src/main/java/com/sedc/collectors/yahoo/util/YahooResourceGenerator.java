package com.sedc.collectors.yahoo.util;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.UrlResource;

import java.time.LocalDate;
import java.util.List;

public class YahooResourceGenerator implements ItemWriter<String> {

    private static final Logger LOG = Logger.getLogger(YahooResourceGenerator.class);

    private List<UrlResource> urls;

    private String sourceEngineName;

    private LocalDate startDate;

    @Override
    public void write(List<? extends String> list) throws Exception {
        UrlResource resource;

        switch (sourceEngineName) {
            case "YAHOO_HISTORICAL":
                resource = YahooResourceHelper.getHistoricalDataResource((List<String>) list, startDate, LocalDate.now());
                break;
            case "YAHOO_QUOTE":
                resource = YahooResourceHelper.getQuoteResource((List<String>) list);
                break;
            case "YAHOO_XCHANGE":
                resource = YahooResourceHelper.getXchangeResource((List<String>) list);
                break;
            default:
                throw new UnsupportedOperationException("Source engine " + sourceEngineName + " is unimplemented");
        }
        LOG.debug("Generated resource: " + resource);
        urls.add(resource);
    }

    public void setUrls(List<UrlResource> urls) {
        this.urls = urls;
    }

    public void setSourceEngineName(String sourceEngineName) {
        this.sourceEngineName = sourceEngineName;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
