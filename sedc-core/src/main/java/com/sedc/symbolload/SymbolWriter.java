package com.sedc.symbolload;

import com.sedc.core.model.Symbol;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.batch.item.database.HibernateItemWriter;
import org.springframework.orm.hibernate3.HibernateOperations;

import java.util.List;

public class SymbolWriter extends HibernateItemWriter<Symbol> {

    @Override
    protected void doWrite(SessionFactory sessionFactory, List<? extends Symbol> items) {
        if (logger.isDebugEnabled()) {
            logger.debug("Writing to Hibernate with " + items.size()
                    + " items.");
        }

        Session currentSession = sessionFactory.getCurrentSession();

        if (!items.isEmpty()) {
            long saveOrUpdateCount = 0;
            for (Symbol item : items) {

                Number count = (Number) currentSession.createCriteria(Symbol.class)
                        .add(Restrictions.eq("name", item.getName()))
                        .add(Restrictions.eq("exchange", item.getExchange()))
                        .setProjection(Projections.rowCount())
                        .uniqueResult();


                if (count.intValue() != 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Symbol is already saved " + item);
                    }
                    continue;
                }

                currentSession.save(item);
                saveOrUpdateCount++;
            }
            if (logger.isDebugEnabled()) {
                logger.debug(saveOrUpdateCount + " entities saved/updated.");
                logger.debug((items.size() - saveOrUpdateCount)
                        + " entities found in session.");
            }
        }
    }

    @Override
    protected void doWrite(HibernateOperations hibernateTemplate, List<? extends Symbol> items) {
        throw new UnsupportedOperationException();
    }
}
