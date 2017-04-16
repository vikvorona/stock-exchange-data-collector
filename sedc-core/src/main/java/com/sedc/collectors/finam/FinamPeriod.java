package com.sedc.collectors.finam;

/**
 * Created by oshulyakov on 3/1/2017.
 */
public enum FinamPeriod {

    TIK("0", 1), //TODO: for finam there is another format
    ONE_MINUTE("1", 2),
    FIVE_MINUTE("5", 3),
    TEN_MINUTE("10", 4),
    FIFTEEN_MINUTE("15", 5),
    HALF_HOUR("30", 6),
    ONE_HOUR("60", 7),
    ONE_DAY("D", 8),
    ONE_WEEK("W", 9),
    ONE_MONTH("M", 10);

    private String value;
    private Integer code;

    FinamPeriod(String value, Integer code) {
        this.value = value;
        this.code = code;
    }

    public static FinamPeriod getInstance(String value) {

        for (FinamPeriod p : FinamPeriod.values()) {
            if (p.getValue().equalsIgnoreCase(value)) {
                return p;
            }
        }

        throw new IllegalArgumentException(value);
    }

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }
}
