package com.sedc.collectors.finam.symbol;

import com.sedc.collectors.finam.symbol.model.FinamJaxbSecurity;
import com.sedc.core.model.Exchange;
import com.sedc.core.model.Symbol;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FinamSymbolLoadProcessor implements ItemProcessor<FinamJaxbSecurity, Symbol>, InitializingBean, DisposableBean {

    private static final Logger LOG = Logger.getLogger(FinamSymbolLoadProcessor.class);

    @Setter
    private SessionFactory sessionFactory;

    private Session session;

    private Map<String, Exchange> exchangeCache = new HashMap<>();

    @Override
    public Symbol process(FinamJaxbSecurity item) throws Exception {

        if (Objects.isNull(item)) return null;

        if (Objects.isNull(item.getCode())){
            LOG.warn("Security does not have code: " + item);
            return null;
        }

        String board = item.getBoard();
        if (Objects.isNull(board)){
            LOG.warn("Security does not have exchange: " + item.getCode());
            return null;
        }

        Symbol o = new Symbol();
        o.setName(item.getCode());
        o.setDescription(item.getName());

        board = board.toUpperCase();
        if (!exchangeCache.containsKey(board)){
            Exchange e = (Exchange) session.createQuery("from Exchange where name = :name")
                    .setString("name", board)
                    .uniqueResult();

            exchangeCache.put(board, e);
        }
        Exchange e = exchangeCache.get(board);
        if (Objects.isNull(e)){
            LOG.warn("Exchange is not found: " + board);
            return null;
        }
        o.setExchange(e);

        LOG.debug("Mapped symbol: " + o);

        return o;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        session = sessionFactory.openSession();
    }

    @Override
    public void destroy() throws Exception {
        session.close();
    }
}
