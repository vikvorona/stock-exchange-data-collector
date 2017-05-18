package com.sedc.collectors.yahoo.historical;

import com.sedc.collectors.yahoo.historical.model.YahooHistoricalRecord;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class YahooHibernateItemPreparedStatementSetter implements ItemPreparedStatementSetter<YahooHistoricalRecord> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    public void setValues(YahooHistoricalRecord item, PreparedStatement ps) throws SQLException {
        if (item == null) {
            return;
        }

        int i = 0;

        ps.setString(++i, item.getSymbol());

        try {
            ps.setObject(++i, dateFormat.parse(item.getDate()), Types.DATE);
        } catch (Exception e) {
            throw new SQLException(e);
        }

        ps.setObject(++i, item.getOpen(), Types.DOUBLE);
        ps.setObject(++i, item.getHigh(), Types.DOUBLE);
        ps.setObject(++i, item.getLow(), Types.DOUBLE);
        ps.setObject(++i, item.getClose(), Types.DOUBLE);
        ps.setObject(++i, item.getVolume(), Types.DOUBLE);
    }
}
