package com.sedc.collectors.finam.historical;

import com.sedc.collectors.finam.FinamUtils;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public void write(List<? extends String> items) throws Exception {

        String startDate = dateFormat.format(dateFrom);
        String endDate = dateFormat.format(dateTo);
        String startDay = getDay(dateFrom);
        String startMonth = getMonth(dateFrom);
        String startYear = getYear(dateFrom);
        String endDay = getDay(dateTo);
        String endMonth = getMonth(dateTo);
        String endYear = getYear(dateTo);

        for (String symbol : items) {

            String queryParams[] = {"1", "16842", symbol, "0", startDay, startMonth, startYear, startDate, endDay, endMonth, endYear,
                    endDate, "5", symbol + new Date(), ".txt", symbol, "1", "1", "1", "on", "1", "1", "1", "1"};

            UrlResource resource = new UrlResource(FinamUtils.buildUrl(queryParams));
            LOG.debug("Generated resource: " + resource);
            urls.add(resource);
        }
    }

    private String getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    private String getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return String.valueOf(month);
    }

    private String getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(day);
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
