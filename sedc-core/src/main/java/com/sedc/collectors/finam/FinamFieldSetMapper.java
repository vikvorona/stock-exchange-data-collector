package com.sedc.collectors.finam;

import com.sedc.collectors.finam.model.FinamApiRecord;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by oshulyakov on 3/1/2017.
 */
public class FinamFieldSetMapper implements FieldSetMapper<FinamApiRecord> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hhmmss");

    @Override
    public FinamApiRecord mapFieldSet(FieldSet fieldSet) throws BindException {

        FinamApiRecord record = new FinamApiRecord();
        Integer i = 0;
        record.setTicker(fieldSet.readString(i));

        String period = fieldSet.readString(++i);
        record.setPeriod(FinamPeriods.getInstance(period));

        String date = fieldSet.readString(++i);
        try {
            record.setDate(dateFormat.parse(date));
        } catch (ParseException e) {
            throw new BindException(fieldSet, "FinamApiRecord");
        }

        String time = fieldSet.readString(++i);
        try {
            record.setTime((timeFormat.parse(time)));
        } catch (ParseException e) {
            throw new BindException(fieldSet, "FinamApiRecord");
        }

        record.setOpen(fieldSet.readDouble(++i));
        record.setHigh(fieldSet.readDouble(++i));
        record.setLow(fieldSet.readDouble(++i));
        record.setClose(fieldSet.readDouble(++i));
        record.setVolume(fieldSet.readDouble(++i));

        return record;
    }
}
