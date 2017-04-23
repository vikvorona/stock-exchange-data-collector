package com.sedc.core;

import lombok.Setter;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.batch.item.ItemReader;

public class JbdcBatchUpdater implements ItemReader {

    @Setter
    private SessionFactory sessionFactory;

    @Setter
    private String sql;

    @Override
    public Object read() throws Exception {
        Session session = sessionFactory.openSession();
        session.setFlushMode(FlushMode.COMMIT);
        Transaction t = session.getTransaction();
        t.begin();
        try {
            session.createSQLQuery(sql).executeUpdate();
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
        return null;
    }
}
