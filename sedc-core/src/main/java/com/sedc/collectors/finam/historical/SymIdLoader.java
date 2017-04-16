package com.sedc.collectors.finam.historical;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.batch.item.ItemReader;

public class SymIdLoader implements ItemReader<Object> {
    private static final Logger LOG = Logger.getLogger(SymIdLoader.class);

    private SessionFactory sessionFactory;

    @Override
    public Object read() throws Exception {
        Session session = sessionFactory.openSession();
        Transaction t = session.getTransaction();

        t.begin();
        try {
            session.createSQLQuery("" +
                    "update STAGE_FINAM_HISTORICAL\n" +
                    "set sym_id=subquery.sym_id\n" +
                    "from (select s.name, s.sym_id\n" +
                    "   from SYMBOL s, stage_finam_historical\n" +
                    "   where s.name = stage_finam_historical.symbol) as subquery\n" +
                    "   where subquery.name = stage_finam_historical.symbol;")
                    .executeUpdate();
            session.flush();
            LOG.info("update sym_id");
            t.commit();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            t.rollback();
        }
        session.close();

        return null;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
