package com.sedc.collectors.finam.historical;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemReader;

import java.util.Date;

public class SnapshotTableLoader implements ItemReader<Object> {

    private static final Logger LOG = Logger.getLogger(SnapshotTableLoader.class);

    private SessionFactory sessionFactory;

    @Override
    public Object read() {
        Session session = sessionFactory.openSession();
        session.createSQLQuery("" +
                "INSERT INTO snapshot_historical (scei_id, business_date, sym_id, period, date, time, open, high, low, close, volume)\n" +
                "SELECT :scei_id, :business_date, sym_id, per, date, time, open, high, low, close, volume\n" +
                "FROM stage_finam_historical\n" +
                "WHERE ACTIVE_FLAG = 'Y'")
                .setLong("scei_id", 1l) //TODO: put value from SourceCenterEngineInstance
                .setDate("business_date", new Date())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();

        return null;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
