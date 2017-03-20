CREATE TABLE STAGE_YAHOO_FXRATE (
     HASH         NUMERIC(18),
     ID           VARCHAR(20) NOT NULL,
     NAME         VARCHAR(20) NOT NULL,
     RATE         NUMERIC(18),
     DATE         DATE NOT NULL,
     TIME         TIME NOT NULL,
     ASK          NUMERIC(18),
     BID          NUMERIC(18),
     SYM_ID       NUMERIC(18),

     CONSTRAINT IPK_STAGE_YAHOO_FXRATE PRIMARY KEY (ID, NAME, DATE, TIME, SYM_ID)
);