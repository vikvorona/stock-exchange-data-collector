CREATE TABLE STAGE_FINAM_HISTORICAL (
                  HASH          NUMERIC(18),
                  SYMBOL        VARCHAR(20) NOT NULL,
                  SYM_ID        VARCHAR(20),
                  PER           VARCHAR(5) NOT NULL,
                  DATE          DATETIME NOT NULL,
                  TIME          DATETIME NOT NULL,
                  OPEN          NUMERIC(18),
                  HIGH          NUMERIC(18),
                  LOW           NUMERIC(18),
                  CLOSE         NUMERIC(18),
                  VOLUME        NUMERIC(18),
                  ACTIVE_FLAG       CHAR(1) DEFAULT 'Y' NOT NULL,
                  ACTIVE_REASON     CHAR(1)
);
CREATE UNIQUE INDEX IUK_STAGE_FINAM_HISTORICAL_NAME ON STAGE_FINAM_HISTORICAL (SYMBOL, PER, DATE, TIME);