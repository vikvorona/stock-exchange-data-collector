package com.sedc.collectors.finam.historical;

import com.sedc.collectors.finam.historical.model.FinamApiRecord;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

public class FinamApiProcessor implements ItemProcessor<FinamApiRecord, FinamApiRecord> {

    private static final Logger LOG = Logger.getLogger(FinamApiProcessor.class);

    @Override
    public FinamApiRecord process(FinamApiRecord item) throws Exception {
        LOG.info("Processing item=" + item);

        //TODO: make logic here
        return item;
    }
}
