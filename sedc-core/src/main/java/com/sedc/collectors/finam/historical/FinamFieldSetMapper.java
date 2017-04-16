package com.sedc.collectors.finam.historical;

import com.sedc.collectors.finam.historical.model.FinamApiRecord;
import org.apache.log4j.Logger;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by oshulyakov on 3/1/2017.
 */
public class FinamFieldSetMapper implements FieldSetMapper<FinamApiRecord> {

    private static final Logger LOG = Logger.getLogger(FinamFieldSetMapper.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hhmmss");

    @Override
    public FinamApiRecord mapFieldSet(FieldSet fieldSet) {

        FinamApiRecord record = new FinamApiRecord();
        record.setTicker(fieldSet.readString("ticker"));
        record.setPeriod(fieldSet.readString("period"));

        String date = fieldSet.readString("date");
        try {
            record.setDate(dateFormat.parse(date));
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
            record.setDate(null);
        }

        String time = fieldSet.readString("time");
        try {
            record.setTime((timeFormat.parse(time)));
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
            record.setTime(null);
        }

        record.setOpen(fieldSet.readDouble("open"));
        record.setHigh(fieldSet.readDouble("high"));
        record.setLow(fieldSet.readDouble("low"));
        record.setClose(fieldSet.readDouble("close"));
        record.setVolume(fieldSet.readDouble("volume"));

        return record;
    }
}
