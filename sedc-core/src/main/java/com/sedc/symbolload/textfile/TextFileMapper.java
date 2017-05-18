package com.sedc.symbolload.textfile;

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

public class TextFileMapper implements ItemProcessor<FieldSet, Symbol>, InitializingBean {

    private static final Logger LOG = Logger.getLogger(TextFileMapper.class);

    @Setter
    private SessionFactory sessionFactory;

    @Setter
    private String exchangeName;

    private Exchange exchange;

    @Override
    public Symbol process(FieldSet item) throws Exception {
        if (Objects.isNull(item)) {
            return null;
        }

        if (Objects.isNull(item.readString("Symbol"))
                || "Symbol".equals(item.readString("Symbol"))
                || Objects.isNull(item.readString("Description"))) {
            LOG.warn("Not valid " + exchangeName + " row: " + item);
            return null;
        }

        Symbol s = new Symbol();
        s.setName(item.readString("Symbol"));
        s.setDescription(item.readString("Description"));
        s.setExchange(exchange);

        LOG.debug("Mapped symbol: " + s);

        return s;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Session session = sessionFactory.openSession();

        exchange = (Exchange) session.createQuery("from Exchange where name = :name")
                .setString("name", exchangeName)
                .uniqueResult();

        session.close();

        Assert.notNull(exchange, "Cannot find " + exchangeName + " exchange");
    }
}
