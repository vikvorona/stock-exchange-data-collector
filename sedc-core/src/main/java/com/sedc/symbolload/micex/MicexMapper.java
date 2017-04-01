package com.sedc.symbolload.micex;

import com.sedc.core.model.Exchange;
import com.sedc.core.model.Symbol;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Objects;

public class MicexMapper implements ItemProcessor<MicexRow, Symbol>, InitializingBean {

    private static final Logger LOG = Logger.getLogger(MicexMapper.class);

    @Setter
    private SessionFactory sessionFactory;

    private Exchange micex;

    @Override
    public Symbol process(MicexRow item) throws Exception {

        if (Objects.isNull(item) || Objects.isNull(item.getSecid()) || Objects.isNull(item.getShortname())) {
            LOG.warn("Not valid MICEX row: " + item);
            return null;
        }

        Symbol s = new Symbol();
        s.setName(item.getSecid());
        s.setDescription(item.getSecname());
        s.setIsin(item.getIsin());
        s.setExchange(micex);

        LOG.debug("Mapped symbol: " + s);

        return s;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Session session = sessionFactory.openSession();

        micex = (Exchange) session.createQuery("from Exchange where name = :name")
                .setString("name", "MICEX")
                .uniqueResult();

        session.close();

        Assert.notNull(micex, "Cannot fined MICEX exchange");
    }
}
