package com.sedc.collectors.finam;

import org.springframework.util.StringUtils;

/**
 * Created by oshulyakov on 3/1/2017.
 */
public enum FinamPeriod {

    TIK("0"), //TODO: for finam there is another format
    ONE_MINUTE("1"),
    FIVE_MINUTE("5"),
    TEN_MINUTE("10"),
    FIFTEEN_MINUTE("15"),
    HALF_HOUR("30"),
    ONE_HOUR("60"),
    ONE_DAY("D"),
    ONE_WEEK("W"),
    ONE_MONTH("M");

    private String value;

    FinamPeriod(String value) {
        this.value = value;
    }

    public static FinamPeriod getInstance(String value) {

        if (StringUtils.isEmpty(value))
            return null;

        for (FinamPeriod p : FinamPeriod.values()) {
            if (p.getValue().equalsIgnoreCase(value)) {
                return p;
            }
        }

        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
