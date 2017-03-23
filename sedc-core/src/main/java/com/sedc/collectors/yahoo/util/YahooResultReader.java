package com.sedc.collectors.yahoo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Iterator;

public class YahooResultReader<T> implements ItemReader<T> {
    private String resultKey;
    private Class<T> targetClass;
    private Resource resource;

    private ObjectMapper mapper;
    private Iterator<JsonNode> it;

    public YahooResultReader(String resultKey, Class<T> targetClass) {
        this.resultKey = resultKey;
        this.targetClass = targetClass;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        //TODO: make sure this fits the contract
        //TODO: maybe use streaming api instead for smaller first read overhead
        if (it == null) {
            init();
        }
        if (it.hasNext()) {
            return mapper.treeToValue(it.next(), targetClass);
        }
        return null;
    }

    private void init() throws IOException {
        mapper = new ObjectMapper();
        it = mapper.readTree(resource.getInputStream()).get("query").get("results").get(resultKey).elements();
    }


}
