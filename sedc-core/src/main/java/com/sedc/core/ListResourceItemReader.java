package com.sedc.core;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;

import java.util.List;

public class ListResourceItemReader extends MultiResourceItemReader {

    @Autowired(required = false)
    private List<UrlResource> resources;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        super.setResources(resources.toArray(new UrlResource[resources.size()]));
        super.open(executionContext);
    }

    public void setResources(List<UrlResource> resources) {
        this.resources = resources;
    }
}
