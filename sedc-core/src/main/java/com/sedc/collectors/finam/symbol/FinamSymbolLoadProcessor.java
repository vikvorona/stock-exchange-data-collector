package com.sedc.collectors.finam.symbol;

import com.sedc.collectors.finam.symbol.model.FinamJaxbSecurity;
import com.sedc.core.model.Exchange;
import com.sedc.core.model.Symbol;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

public class FinamSymbolLoadProcessor implements ItemProcessor<FinamJaxbSecurity, Symbol> {

    private static final Logger LOG = Logger.getLogger(FinamSymbolLoadProcessor.class);

    @Override
    public Symbol process(FinamJaxbSecurity item) throws Exception {

        if (Objects.isNull(item)) return null;

        Symbol o = new Symbol();
        o.setName(item.getCode());
        o.setDescription(item.getName());

        //TODO: load exchanges from database
        Exchange e = new Exchange();
        e.setName(item.getBoard());
        e.setCountry("RUSSIA");

        o.setExchange(e);

        LOG.debug("Mapped symbol: " + o);

        return o;
    }
}
