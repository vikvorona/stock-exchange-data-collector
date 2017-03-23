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

    public void write(List<? extends String> items) throws Exception {

        for (String symbol : items) {

            String queryParams[] = {"1", "16842", symbol, "0", "24", "1", "2017", "24.02.2017", "1", "2", "2017",
                    "01.03.2017", "5", symbol + new Date(), ".txt", symbol, "1", "1", "1", "on", "1", "1", "1", "1"};

            UrlResource resource = new UrlResource(FinamUtils.buildUrl(queryParams));
            LOG.debug("Generated resource: " + resource);
            urls.add(resource);
        }
    }

    public void setUrls(List<UrlResource> urls) {
        this.urls = urls;
    }
}
