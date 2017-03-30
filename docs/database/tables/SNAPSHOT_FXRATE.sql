CREATE TABLE SNAPSHOT_FXRATE (
     SX_ID             NUMERIC(18) NOT NULL DEFAULT nextval('S_SNAPSHOT_FXRATE_PK'),
     SCEI_ID           NUMERIC(18) NOT NULL,
     SYM_ID            NUMERIC(18) NOT NULL,
     BUSINESS_DATE     DATE NOT NULL,
     ID                VARCHAR(20) NOT NULL,
     NAME              VARCHAR(20) NOT NULL,
     DATE              DATE NOT NULL,
     TIME              TIME NOT NULL,
     ASK               NUMERIC(18) NOT NULL,
     BID               NUMERIC(18) NOT NULL,

     CONSTRAINT IPK_SNAPSHOT_FXRATE PRIMARY KEY (SX_ID)
);

ALTER TABLE SNAPSHOT_FXRATE ADD CONSTRAINT IFK_SYMBOL FOREIGN KEY (SYM_ID) REFERENCES SYMBOL (SYM_ID);

ALTER TABLE SNAPSHOT_FXRATE ADD CONSTRAINT IFK_SOURCE_CENTER_ENGINE_INSTANCE FOREIGN KEY (SCEI_ID) REFERENCES SOURCE_CENTER_ENGINE_INSTANCE (SCEI_ID);