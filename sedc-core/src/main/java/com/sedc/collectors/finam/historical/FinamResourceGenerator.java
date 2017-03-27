package com.sedc.collectors.finam.historical;

import com.sedc.collectors.finam.FinamUtils;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;

import java.util.Date;
import java.util.List;

public class FinamResourceGenerator implements ItemWriter<String> {

    private static final Logger LOG = Logger.getLogger(FinamResourceGenerator.class);

    @Autowired
    private List<UrlResource> urls;
    @Autowired
    private String period;
    @Autowired
    private String region;
    @Autowired
    private Date dateFrom;
    @Autowired
    private Date dateTo;


    public void write(List<? extends String> items) throws Exception {

        for (String symbol : items) {
            UrlResource resource = new UrlResource(FinamUtils.buildUrl(symbol, dateFrom, dateTo));
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

    public void setRegion(String region) {
        this.region = region;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
