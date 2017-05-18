package com.sedc.collectors.yahoo.quote;

import com.sedc.collectors.yahoo.quote.model.YahooQuoteRecord;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YahooQuoteItemPreparedStatementSetter implements ItemPreparedStatementSetter<YahooQuoteRecord> {

    private Pattern marketCapitalizationPattern = Pattern.compile("(\\d+\\.\\d*)([a-zA-Z]*)");
    private Pattern daysRangePattern = Pattern.compile("(\\d+\\.\\d*) - (\\d+\\.\\d*)");

    @Override
    public void setValues(YahooQuoteRecord item, PreparedStatement ps) throws SQLException {

        if (ps == null || item == null)
            return;

        int i = 0;

        ps.setString(++i, item.getSymbol());
        ps.setObject(++i, item.getAverageDailyVolume(), Types.DOUBLE);
        ps.setObject(++i, item.getChange(), Types.DOUBLE);
        ps.setObject(++i, item.getDaysLow(), Types.DOUBLE);
        ps.setObject(++i, item.getDaysHigh(), Types.DOUBLE);
        ps.setObject(++i, item.getYearLow(), Types.DOUBLE);
        ps.setObject(++i, item.getYearHigh(), Types.DOUBLE);

        String marketCapitalization = item.getMarketCapitalization();
        Matcher marketCapitalizationMatcher = marketCapitalizationPattern.matcher(marketCapitalization);
        if (marketCapitalizationMatcher.matches()) {
            Double value = Double.valueOf(marketCapitalizationMatcher.group(1));
            String multiplier = marketCapitalizationMatcher.group(2);

            switch (multiplier) {
                case "B":
                    value = value * 1_000_000_000;
                    break;
                case "M":
                    value = value * 1_000_000;
                    break;
                case "K":
                    value = value * 1_000;
                    break;
            }
            ps.setDouble(++i, value);
        } else {
            ps.setNull(++i, Types.DOUBLE);
        }

        ps.setObject(++i, item.getLastTradePriceOnly());

        String daysRange = item.getDaysRange();
        Matcher daysRangeMatcher = daysRangePattern.matcher(daysRange);
        if (daysRangeMatcher.matches()) {
            ps.setDouble(++i, Double.valueOf(daysRangeMatcher.group(1)));
            ps.setDouble(++i, Double.valueOf(daysRangeMatcher.group(2)));
        } else {
            ps.setNull(++i, Types.DOUBLE);
            ps.setNull(++i, Types.DOUBLE);
        }

        ps.setString(++i, item.getName());
        ps.setObject(++i, item.getVolume(), Types.DOUBLE);
        ps.setString(++i, item.getStockExchange());
    }
}
