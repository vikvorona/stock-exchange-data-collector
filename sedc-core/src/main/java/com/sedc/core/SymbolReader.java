package com.sedc.core;

import com.sedc.managers.SymbolManager;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class SymbolReader implements ItemReader<String>, InitializingBean, DisposableBean {

    private static final Logger LOG = Logger.getLogger(SymbolReader.class);

    @Autowired
    private SymbolManager symbolManager;
    @Autowired
    private String region;

    private Queue<String> buffer = new ArrayDeque<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<String> symbols = symbolManager.getStringSymbolsBySource(region);
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

    public void setRegion(String region) {
        this.region = region;
    }
}
