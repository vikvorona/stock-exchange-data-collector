CREATE TABLE SNAPSHOT_HISTORICAL (
                             SCEI_ID         NUMERIC(18) NOT NULL,
                             BUSINESS_DATE   NUMERIC(18) NOT NULL,
                             SYM_ID          NUMERIC(18) NOT NULL,
                             PER             NUMERIC(18) NOT NULL,
                             DATE            NUMERIC(18) NOT NULL,
                             TIME            NUMERIC(18) NOT NULL,
                             OPEN            NUMERIC(18) NOT NULL,
                             HIGH            NUMERIC(18) NOT NULL,
                             LOW             NUMERIC(18) NOT NULL,
                             CLOSE           NUMERIC(18) NOT NULL,
                             VOLUME          NUMERIC(18) NOT NULL,
                             ACTIVE_FLAG     CHAR(1) DEFAULT 'Y' NOT NULL,

CONSTRAINT IPK_SNAPSHOT_HISTORICAL PRIMARY KEY (SCEI_ID) DEFAULT nextval('S_SNAPSHOT_HISTORICAL_PK')
                           ) TABLESPACE GENERAL_INDEX;

CREATE UNIQUE INDEX IUK_SNAPSHOT_HISTORICAL_NAME ON SNAPSHOT_HISTORICAL (TYPE, NAME) TABLESPACE GENERAL_INDEX;
