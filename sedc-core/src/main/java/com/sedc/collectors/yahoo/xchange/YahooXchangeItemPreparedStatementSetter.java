package com.sedc.collectors.yahoo.xchange;

import com.sedc.collectors.yahoo.xchange.model.YahooXchangeRecord;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class YahooXchangeItemPreparedStatementSetter implements ItemPreparedStatementSetter<YahooXchangeRecord> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("h:mma", Locale.US);

    @Override
    public void setValues(YahooXchangeRecord item, PreparedStatement ps) throws SQLException {
        if (ps == null || item == null)
            return;

        int i = 0;

        if ("N/A".equalsIgnoreCase(item.getName())){
            ps.setString(++i, item.getId());
            ps.setString(++i, item.getName());
            ps.setNull(++i, Types.DOUBLE);
            ps.setObject(++i, new Date(0), Types.DATE);
            ps.setObject(++i, new Date(0), Types.TIME);
            ps.setNull(++i, Types.DOUBLE);
            ps.setNull(++i, Types.DOUBLE);

            return;
        }

        ps.setString(++i, item.getId());
        ps.setString(++i, item.getName());
        ps.setObject(++i, item.getRate(), Types.DOUBLE);

        try {
            ps.setObject(++i, dateFormat.parse(item.getDate()), Types.DATE);
            ps.setObject(++i, timeFormat.parse(item.getTime()), Types.TIME);
        } catch (ParseException e) {
            throw new SQLException(e);
        }

        ps.setObject(++i, item.getAsk(), Types.DOUBLE);
        ps.setObject(++i, item.getBid(), Types.DOUBLE);
    }
}
