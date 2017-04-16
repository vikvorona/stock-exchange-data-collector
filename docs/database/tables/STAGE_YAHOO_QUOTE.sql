CREATE TABLE STAGE_YAHOO_QUOTE(
    HASH                       NUMERIC(18),
    SYMBOL                     VARCHAR(20) NOT NULL,
    SYM_ID                     NUMERIC(18),
    AVG_DAILY_VOLUME           NUMERIC(18,2),
    CHANGE                     NUMERIC(18,2),
    DAYS_LOW                   NUMERIC(18,2),
    DAYS_HIGH                  NUMERIC(18,2),
    YEAR_LOW                   NUMERIC(18,2),
    YEAR_HIGH                  NUMERIC(18,2),
    MARKET_CAPITALIZATION      NUMERIC(18,2),
    LAST_TRADE_PRICE           NUMERIC(18,2),
    DAYS_RANGE_FROM            NUMERIC(18,2),
    DAYS_RANGE_TO              NUMERIC(18,2),
    NAME                       VARCHAR(100),
    VOLUME                     NUMERIC(18,2),
    STOCK_EXCHANGE             VARCHAR(18),
    EX_ID                      NUMERIC(18),
    ACTIVE_FLAG                CHAR(1) DEFAULT 'Y' NOT NULL CHECK (ACTIVE_FLAG in ('Y','N') ),
    ACTIVE_REASON              VARCHAR(100),

    CONSTRAINT IPK_STAGE_YAHOO_QUOTE PRIMARY KEY (SYMBOL)
);
