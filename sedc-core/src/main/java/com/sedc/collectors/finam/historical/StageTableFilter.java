package com.sedc.collectors.finam.historical;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

public class StageTableFilter implements ItemReader<Object> {

    private static final Logger LOG = Logger.getLogger(StageTableFilter.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Object read() throws Exception {

        Session session = sessionFactory.openSession();

//        session.createSQLQuery("")
//            .executeUpdate();

        session.close();

        return null;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
