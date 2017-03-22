CREATE TABLE STAGE_YAHOO_QUOTE(
    HASH                       NUMERIC(18),
    SYMBOL                     VARCHAR(20) NOT NULL,
    SYM_ID                     NUMERIC(18),
    AVG_DAILY_VOLUME           NUMERIC(18),
    CHANGE                     NUMERIC(18),
    DAYS_LOW                   NUMERIC(18),
    DAYS_HIGH                  NUMERIC(18),
    YEAR_LOW                   NUMERIC(18),
    YEAR_HIGH                  NUMERIC(18),
    MARKET_CAPITALIZATION      NUMERIC(18),
    LAST_TRADE_PRICE           NUMERIC(18),
    DAYS_RANGE_FROM            NUMERIC(18),
    DAYS_RANGE_TO              NUMERIC(18),
    NAME                       VARCHAR(20),
    VOLUME                     NUMERIC(18),
    STOCK_EXCHANGE             VARCHAR(18),
    EX_ID                      NUMERIC(18),
    CREATED_TM                 TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),

     CONSTRAINT IPK_STAGE_YAHOO_QUOTE PRIMARY KEY (SYMBOL)
);
