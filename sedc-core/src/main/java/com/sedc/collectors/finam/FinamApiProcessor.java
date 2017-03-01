package com.sedc.collectors.finam;

import com.sedc.collectors.finam.model.FinamApiRecord;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by oshulyakov on 2/28/2017.
 */
@Log4j
public class FinamApiProcessor implements ItemProcessor<FinamApiRecord, FinamApiRecord> {

    private static final Logger LOG = Logger.getLogger(FinamApiProcessor.class);

    @Override
    public FinamApiRecord process(FinamApiRecord item) throws Exception {
        LOG.info("Processing item=" + item);
        return item;
    }
}
