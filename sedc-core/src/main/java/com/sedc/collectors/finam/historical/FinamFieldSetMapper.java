package com.sedc.collectors.finam.historical;

import com.sedc.collectors.finam.FinamException;
import com.sedc.collectors.finam.FinamPeriod;
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
    private static final String WRONG_DATE_EXCEPTION = "Wrong date format.";
    private static final String WRONG_TIME_EXCEPTION = "Wrong time format.";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");

    @Override
    public FinamApiRecord mapFieldSet(FieldSet fieldSet) {

        dateFormat.setLenient(false);
        timeFormat.setLenient(false);
        FinamApiRecord record = new FinamApiRecord();
        record.setTicker(fieldSet.readString("ticker"));
        String period = fieldSet.readString("period");
        record.setPeriod(FinamPeriod.getInstance(period).getValue());

        String date = fieldSet.readString("date");
        try {
            record.setDate(dateFormat.parse(date));
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
            record.setDate(null);
            throw new FinamException(WRONG_DATE_EXCEPTION);
        }

        String time = fieldSet.readString("time");
        try {
            record.setTime((timeFormat.parse(time)));
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
            record.setTime(null);
            throw new FinamException(WRONG_TIME_EXCEPTION);
        }

        record.setOpen(fieldSet.readDouble("open"));
        record.setHigh(fieldSet.readDouble("high"));
        record.setLow(fieldSet.readDouble("low"));
        record.setClose(fieldSet.readDouble("close"));
        record.setVolume(fieldSet.readDouble("volume"));

        return record;
    }
}
