CREATE TABLE STAGE_YAHOO_HISTORICAL(
    SYMBOL              VARCHAR(20) NOT NULL,
    SYM_ID              NUMERIC(18),
    PER                 VARCHAR(20) NOT NULL,
    DATE                DATE NOT NULL,
    OPEN                NUMERIC(18,2),
    HIGH                NUMERIC(18,2),
    LOW                 NUMERIC(18,2),
    CLOSE               NUMERIC(18,2),
    VOLUME              NUMERIC(18,2),
    ACTIVE_FLAG         CHAR(1) DEFAULT 'Y' NOT NULL CHECK (ACTIVE_FLAG in ('Y','N') ),
    ACTIVE_REASON       VARCHAR(20),

    CONSTRAINT IPK_STAGE_YAHOO_HISTORICAL PRIMARY KEY (SYMBOL, PER, DATE, ACTIVE_FLAG)
);