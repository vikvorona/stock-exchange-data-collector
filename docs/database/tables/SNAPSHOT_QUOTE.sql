CREATE TABLE SNAPSHOT_QUOTE (
     SQ_ID                    NUMERIC(18) NOT NULL,
     SCEI_ID                  NUMERIC(18) NOT NULL,
     BUSINESS_DATE            DATETIME NOT NULL,
     SYM_ID                   NUMERIC(18) NOT NULL,
     AVG_DAILY_VOLUME         NUMERIC(18) NOT NULL,
     CHANGE                   NUMERIC(18) NOT NULL,
     DAYS_LOW                 NUMERIC(18) NOT NULL,
     DAYS_HIGH                NUMERIC(18) NOT NULL,
     YEARS_LOW                NUMERIC(18) NOT NULL,
     YEARS_HIGH               NUMERIC(18) NOT NULL,
     MARKET_CAPITALIZATION    NUMERIC(18) NOT NULL,
     LAST_TRADE_PRICE         NUMERIC(18) NOT NULL,
     DAYS_RANGE               NUMERIC(18) NOT NULL,
     NAME                     VARCHAR(20) NOT NULL,
     AVG_DAILY_VOLUME         NUMERIC(40) NOT NULL,

     CONSTRAINT IPK_CODE_GENERIC PRIMARY KEY (SQ_ID) DEFAULT nextval('S_SNAPSHOT_QUOTE_PK')
) TABLESPACE GENERAL_INDEX;

CREATE UNIQUE INDEX IUK_CODE_GENERIC_NAME ON CODE_GENERIC (TYPE, NAME) TABLESPACE GENERAL_INDEX;

ALTER TABLE SNAPSHOT_QUOTE ADD CONSTRAINT IFK_SOURCE_CENTER_ENGINE_INSTANCE FOREIGN KEY (SCEI_ID) REFERENCES SOURCE_CENTER_ENGINE_INSTANCE (SCEI_ID) MATCH FULL;