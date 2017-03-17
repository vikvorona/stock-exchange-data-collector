CREATE TABLE STAGE_YAHOO_QUOTE(
    SYQ_ID                     NUMERIC(18) NOT NULL,
    HASH                       NUMERIC(18) NOT NULL,
    SYMBOL                     VARCHAR(20) NOT NULL,
    SYM_ID                     NUMERIC(18) NOT NULL,
    AVG_DAILY_VOLUME           NUMERIC(18) NOT NULL,
    CHANGE                     NUMERIC(18) NOT NULL,
    DAYS_LOW                   NUMERIC(18) NOT NULL,
    DAYS_HIGH                  NUMERIC(18) NOT NULL,
    YEAR_LOW                   NUMERIC(18) NOT NULL,
    YEAR_HIGH                  NUMERIC(18) NOT NULL,
    MARKET_CAPITALIZATION      NUMERIC(18) NOT NULL,
    LAST_TRADE_PRICE           NUMERIC(18) NOT NULL,
    DAYS_RANGE                 NUMERIC(18) NOT NULL,
    NAME                       VARCHAR(20) NOT NULL,
    VOLUME                     NUMERIC(18) NOT NULL,
    STOCK_EXCHANGE             VARCHAR(18) NOT NULL,
    EX_ID                      NUMERIC(18) NOT NULL,

      CONSTRAINT IPK_STAGE_YAHOO_QUOTE PRIMARY KEY (SYQ_ID) DEFAULT nextval('S_STAGE_YAHOO_QUOTE_PK')
) TABLESPACE GENERAL_INDEX;
