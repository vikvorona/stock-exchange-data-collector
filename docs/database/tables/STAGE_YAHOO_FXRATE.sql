CREATE TABLE STAGE_YAHOO_FXRATE (
     ID           VARCHAR(100) NOT NULL,
     NAME         VARCHAR(100),
     RATE         NUMERIC(18,4),
     DATE         DATE NOT NULL,
     TIME         TIME NOT NULL,
     ASK          NUMERIC(18,4),
     BID          NUMERIC(18,4),
     SYM_ID       NUMERIC(18),
     ACTIVE_FLAG       CHAR(1) DEFAULT 'Y' NOT NULL CHECK (ACTIVE_FLAG in ('Y','N') ),
     ACTIVE_REASON     VARCHAR(20),

     CONSTRAINT IPK_STAGE_YAHOO_FXRATE PRIMARY KEY (ID, DATE, TIME, ACTIVE_FLAG)
);
