package com.sedc.collectors.finam.historical;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.batch.item.ItemReader;

import java.sql.CallableStatement;

public class SnapshotTableLoader implements ItemReader<Object> {

    private static final Logger LOG = Logger.getLogger(SnapshotTableLoader.class);

    private SessionFactory sessionFactory;

    @Override
    public Object read() {
        Session session = sessionFactory.openSession();
        Transaction t = session.getTransaction();
        t.begin();
        session.doWork(connection -> {
            String query = "" +
                    "insert into snapshot_historical (scei_id, business_date, sym_id, period, date, time, open, high, low, close, volume, active_flag) " +
                    "with source_center_engine_instance as (" +
                    "  select scei_id, business_date FROM source_center_engine_instance ORDER BY scei_id DESC LIMIT 1" +
                    " ), stage_finam_historical as (" +
                    "  select sym_id, per, date, time, open, high, low, close, volume, active_flag from stage_finam_historical" +
                    " )" +
                    "select scei.scei_id, scei.business_date, sfh.sym_id, sfh.per, sfh.date, sfh.time, sfh.open, " +
                    "sfh.high, sfh.low, sfh.close, sfh.volume, sfh.active_flag " +
                    "from source_center_engine_instance as scei, stage_finam_historical as sfh";
            CallableStatement statement = connection.prepareCall(query);
            statement.executeUpdate();
        });
        t.commit();
        session.close();

        return null;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
