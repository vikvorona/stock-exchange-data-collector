CREATE TABLE SOURCE_CENTER_ENGINE (
     SCE_ID             NUMERIC(18) NOT NULL DEFAULT nextval('S_SOURCE_CENTER_ENGINE_PK'),
     SC_ID              NUMERIC(18) NOT NULL,
     NAME               VARCHAR(20) NOT NULL,
     REGION_CG_ID       NUMERIC(18),
     CURRENT_COB_SCEI   NUMERIC(18),
     LAST_UPDATE_TM     TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     ACTIVE_FLAG        CHAR(1) DEFAULT 'Y' NOT NULL CHECK (ACTIVE_FLAG in ('Y','N') ),

     CONSTRAINT IPK_SOURCE_CENTER_ENGINE PRIMARY KEY (SCE_ID)
);

ALTER TABLE SOURCE_CENTER_ENGINE ADD CONSTRAINT IFK_SOURCE_CENTER FOREIGN KEY (SC_ID) REFERENCES SOURCE_CENTER (SC_ID);
ALTER TABLE SOURCE_CENTER_ENGINE ADD CONSTRAINT IFK_CODE_GENERIC FOREIGN KEY (REGION_CG_ID) REFERENCES CODE_GENERIC (CG_ID);
