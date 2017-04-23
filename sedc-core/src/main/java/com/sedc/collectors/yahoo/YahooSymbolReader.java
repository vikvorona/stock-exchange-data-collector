package com.sedc.collectors.yahoo;

import com.sedc.core.SymbolReader;
import com.sedc.core.model.CodeGeneric;
import com.sedc.core.model.SourceCenterEngine;
import com.sedc.core.model.SourceCenterEngineInstance;
import lombok.Setter;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by user on 23.04.17.
 */
public class YahooSymbolReader extends SymbolReader implements ItemStreamReader<String> {

    @Setter
    private String sourceCenterName;

    @Setter
    private SourceCenterEngineInstance scei;

    @Setter
    private SessionFactory sessionFactory;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        Session s = sessionFactory.openSession();
        s.setFlushMode(FlushMode.COMMIT);
        Transaction t = s.beginTransaction();

        String name;
        if (sourceCenterName.contains("_")) {
            name = sourceCenterName.split("_")[0];
        } else {
            name = sourceCenterName;
        }

        SourceCenterEngine sourceCenterEngine = (SourceCenterEngine) s.createQuery("from SourceCenterEngine " +
                "where sourceCenter.name = :sourceCenterName " +
                "    and region.name = :region")
                .setString("sourceCenterName", name)
                .setString("region", region)
                .uniqueResult();
        Assert.notNull(sourceCenterEngine, "SourceCenterEngine is not found");
        scei.setSourceCenterEngine(sourceCenterEngine);

        CodeGeneric state = (CodeGeneric) s.createQuery("from CodeGeneric " +
                "where type = :type " +
                "    and name = :name")
                .setString("type", "SOURCE_STATE")
                .setString("name", "INITIAL")
                .uniqueResult();
        Assert.notNull(state, "State is not found");
        scei.setState(state);

        scei.setStartTime(new Timestamp(new Date().getTime()));

        s.saveOrUpdate(scei);
        t.commit();
        s.close();
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {
        Session s = sessionFactory.openSession();
        CodeGeneric state = (CodeGeneric) s.createQuery("from CodeGeneric " +
                "where type = :type " +
                "    and name = :name")
                .setString("type", "SOURCE_STATE")
                .setString("name", "SYMBOLS_READ")
                .uniqueResult();
        Assert.notNull(state, "State is not found");
        scei.setState(state);

        s.saveOrUpdate(scei);
        s.flush();
        s.close();
    }
}
