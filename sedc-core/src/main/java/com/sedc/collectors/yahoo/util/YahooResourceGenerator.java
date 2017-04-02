package com.sedc.collectors.yahoo.util;

import com.sedc.collectors.finam.FinamUtils;
import com.sedc.collectors.yahoo.historical.model.YahooHistoricalRecord;
import com.sedc.collectors.yahoo.quote.model.YahooQuoteRecord;
import com.sedc.managers.SymbolManager;
import com.sedc.managers.SystemVariableManager;
import com.sedc.managers.SystemVariables;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.UrlResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class YahooResourceGenerator implements ItemWriter<String> {

    private static final Logger LOG = Logger.getLogger(YahooResourceGenerator.class);
    private static final String QUOTE_ROOT_ELEMENT = "quote";

    private List<UrlResource> urls;
    private String period;
    private Date dateFrom;
    private Date dateTo;

    private String sourceEngineName;
    private SymbolManager symbolManager;
    private SystemVariableManager systemVariableManager;

    public YahooResourceGenerator(String sourceEngineName,
                                  SymbolManager symbolManager,
                                  SystemVariableManager systemVariableManager) {
        this.sourceEngineName = sourceEngineName;
        this.symbolManager = symbolManager;
        this.systemVariableManager = systemVariableManager;
    }

    public ItemReader getHistoricalReader() throws MalformedURLException {
        List<String> symbols = symbolManager.getStringSymbolsBySource(sourceEngineName);
        LocalDate startDate = LocalDate.parse(systemVariableManager.getSystemVariable(SystemVariables.LAST_UPDATE_DATE),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDate = LocalDate.now();
        UrlResource resource = YahooResourceHelper.getHistoricalDataResource(symbols, startDate, endDate);
        return getStaxReader(QUOTE_ROOT_ELEMENT, YahooHistoricalRecord.class, resource);
    }

    public ItemReader getQuoteReader() throws MalformedURLException {
        List<String> symbols = symbolManager.getStringSymbolsBySource(sourceEngineName);
        UrlResource resource = YahooResourceHelper.getQuoteResource(symbols);
        return getStaxReader(QUOTE_ROOT_ELEMENT, YahooQuoteRecord.class, resource);
    }

    private ItemReader getStaxReader(String fragmentRootElementName, Class classToBeBound, UrlResource resource) {
        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setClassesToBeBound(classToBeBound);
        StaxEventItemReader reader = new StaxEventItemReader();
        reader.setUnmarshaller(unmarshaller);
        reader.setFragmentRootElementName(fragmentRootElementName);
        reader.setResource(resource);
        return reader;
    }

    @Override
    public void write(List<? extends String> list) throws Exception {
        for (String symbol : list) {
            UrlResource resource = new UrlResource(FinamUtils.buildUrl(symbol, dateFrom, dateTo, period));
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

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
