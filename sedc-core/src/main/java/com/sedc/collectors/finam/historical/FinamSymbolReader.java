package com.sedc.collectors.finam.historical;

import com.sedc.managers.SymbolManager;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class FinamSymbolReader implements ItemReader<String>, InitializingBean, DisposableBean {

    private static final Logger LOG = Logger.getLogger(FinamSymbolReader.class);

    @Autowired
    private SymbolManager symbolManager;
    @Autowired
    private String period;
    @Autowired
    private String region;

    private Queue<String> buffer = new ArrayDeque<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<String> symbols = symbolManager.getStringSymbolsBySource("FINAM_HISTORY");
        buffer.addAll(symbols);
    }

    @Override
    public void destroy() throws Exception {
        if (!buffer.isEmpty()){
            LOG.warn("Buffer is not empty");
        }
        buffer.clear();
    }

    @Override
    public String read() throws Exception {
        String symbol = buffer.poll();
        LOG.debug("Polled symbol: " + symbol);
        return symbol;
    }

    public void setSymbolManager(SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
