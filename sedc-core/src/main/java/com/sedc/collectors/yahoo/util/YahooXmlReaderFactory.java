package com.sedc.collectors.yahoo.util;

import com.sedc.collectors.yahoo.historical.model.YahooHistoricalRecord;
import com.sedc.collectors.yahoo.quote.model.YahooQuoteRecord;
import com.sedc.managers.SymbolManager;
import com.sedc.managers.SystemVariableManager;
import com.sedc.managers.SystemVariables;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.UrlResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class YahooXmlReaderFactory {
    private static final String QUOTE_ROOT_ELEMENT = "quote";

    private String sourceEngineName;
    private SymbolManager symbolManager;
    private SystemVariableManager systemVariableManager;

    public YahooXmlReaderFactory(String sourceEngineName,
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
}
