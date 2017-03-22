package com.sedc.collectors.finam.symbol;

import com.sedc.collectors.finam.symbol.model.FinamJaxbSecurity;
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

        LOG.debug("Mapped industry: " + o);

        return null;
    }
}
