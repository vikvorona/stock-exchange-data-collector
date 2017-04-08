package com.sedc.collectors.finam.historical;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.CallableStatement;

public class SourceEngineLoader implements ItemReader<Object> {

    private static final Logger LOG = Logger.getLogger(SourceEngineLoader.class);

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private String region;

    @Override
    public Object read() throws Exception {
        Session session = sessionFactory.openSession();
        session.doWork(connection -> {
            String query = "INSERT INTO SOURCE_CENTER_ENGINE_INSTANCE (sce_id, business_date) " +
                    "VALUES ((select sce_id from source_center_engine " +
                    "join ( " +
                    " select cg_id from code_generic as cg_id where name = '" + region +
                    "') code_generic on region_cg_id = cg_id " +
                    "join ( " +
                    " select sc_id from source_center as sc_id where name = 'FINAM' " +
                    ") source_center on source_center_engine.sc_id = source_center.sc_id), '" +
                    new java.sql.Date(System.currentTimeMillis()) + "');";
            CallableStatement statement = connection.prepareCall(query);
            statement.executeUpdate();
        });
        session.close();

        return null;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
