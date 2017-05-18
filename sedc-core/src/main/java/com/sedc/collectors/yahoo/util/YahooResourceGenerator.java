package com.sedc.collectors.yahoo.util;

import com.sedc.core.model.CodeGeneric;
import com.sedc.core.model.SourceCenterEngineInstance;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.core.io.UrlResource;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

public class YahooResourceGenerator implements ItemStreamWriter<String> {

    private static final Logger LOG = Logger.getLogger(YahooResourceGenerator.class);

    @Setter
    private List<UrlResource> urls;

    @Setter
    private String sourceCenterName;

    @Setter
    private SourceCenterEngineInstance scei;

    @Setter
    private SessionFactory sessionFactory;

    @Setter
    private LocalDate startDate;

    @Setter
    private LocalDate endDate;

    @Override
    public void write(List<? extends String> list) throws Exception {
        UrlResource resource;

        switch (sourceCenterName) {
            case "YAHOO_HISTORICAL":
                resource = YahooResourceHelper.getHistoricalDataResource((List<String>) list, startDate, endDate);
                break;
            case "YAHOO_QUOTE":
                resource = YahooResourceHelper.getQuoteResource((List<String>) list);
                break;
            case "YAHOO_XCHANGE":
                resource = YahooResourceHelper.getXchangeResource((List<String>) list);
                break;
            default:
                throw new UnsupportedOperationException("Source engine " + sourceCenterName + " is unimplemented");
        }
        LOG.debug("Generated resource: " + resource);
        urls.add(resource);
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
    }

    @Override
    public void close() throws ItemStreamException {
        Session s = sessionFactory.openSession();
        s.setFlushMode(FlushMode.COMMIT);
        Transaction t = s.beginTransaction();

        CodeGeneric state = (CodeGeneric) s.createQuery("from CodeGeneric " +
                "where type = :type " +
                "    and name = :name")
                .setString("type", "SOURCE_STATE")
                .setString("name", "RESOURCES_GENERATED")
                .uniqueResult();
        Assert.notNull(state, "State is not found");
        scei.setState(state);

        s.saveOrUpdate(scei);
        t.commit();
        s.close();
    }
}
