package com.sedc.collectors.finam.historical;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.CallableStatement;

public class SnapshotTableLoader implements ItemReader<Object> {

    private static final Logger LOG = Logger.getLogger(SnapshotTableLoader.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Object read() {
        Session session = sessionFactory.openSession();
        session.doWork(connection -> {
            String query = "INSERT INTO SNAPSHOT_HISTORICAL (per, date, time, open, high, low, close, volume, active_flag) " +
                    "select s.per, s.date, s.time, s.open, s.high, s.low, s.close, s.volume, s.active_flag from stage_finam_historical s;";
            CallableStatement statement = connection.prepareCall(query);
            statement.executeUpdate();
        });
        session.getTransaction().commit();
        session.close();

        return null;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
