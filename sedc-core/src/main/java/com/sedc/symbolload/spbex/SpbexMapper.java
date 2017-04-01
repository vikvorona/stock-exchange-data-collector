package com.sedc.symbolload.spbex;


import com.sedc.core.model.Exchange;
import com.sedc.core.model.Symbol;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Objects;

public class SpbexMapper implements ItemProcessor<FieldSet, Symbol>, InitializingBean {

    private static final Logger LOG = Logger.getLogger(SpbexMapper.class);

    @Setter
    private SessionFactory sessionFactory;

    private Exchange spbex;

    @Override
    public Symbol process(FieldSet item) throws Exception {

        if (Objects.isNull(item)) {
            return null;
        }

        if (Objects.isNull(item.readString("s_RTS_code"))
                || "s_RTS_code".equals(item.readString("s_RTS_code"))
                || Objects.isNull(item.readString("e_full_name"))){
            LOG.warn("Not valid SPBEX row: " + item);
            return null;
        }

        Symbol s = new Symbol();
        s.setName(item.readString("s_RTS_code"));
        s.setDescription(item.readString("e_full_name"));
        s.setIsin(item.readString("s_ISIN_code"));
        s.setExchange(spbex);

        LOG.debug("Mapped symbol: " + s);

        return s;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Session session = sessionFactory.openSession();

        spbex = (Exchange) session.createQuery("from Exchange where name = :name")
                .setString("name", "SPBEX")
                .uniqueResult();

        session.close();

        Assert.notNull(spbex, "Cannot fined SPBEX exchange");
    }
}
