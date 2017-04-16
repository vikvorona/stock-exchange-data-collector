package com.sedc.collectors.finam.historical;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.batch.item.ItemReader;

public class SourceEngineLoader implements ItemReader<Object> {

    private static final Logger LOG = Logger.getLogger(SourceEngineLoader.class);

    private SessionFactory sessionFactory;

    private String region;

    private String source;

    @Override
    public Object read() throws Exception {
        Session session = sessionFactory.openSession();
        Transaction t = session.getTransaction();

        t.begin();
        try {
            session.createSQLQuery("" +
                    "INSERT INTO public.source_center_engine_instance (sce_id, business_date, start_tm)\n" +
                    "select sce_id, now(), now()\n" +
                    "from source_center_engine sce\n" +
                    "    join source_center sc on sce.sc_id = sc.sc_id\n" +
                    "    join code_generic cg on sce.region_cg_id = cg.cg_id\n" +
                    "where sc.name = :source\n" +
                    "    and cg.type = 'REGION'\n" +
                    "    and cg.name = :region")
                    .setString("source", source)
                    .setString("region", region)
                    .executeUpdate();
            session.flush();
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

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
