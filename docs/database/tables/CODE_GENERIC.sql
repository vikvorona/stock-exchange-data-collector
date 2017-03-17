CREATE TABLE CODE_GENERIC (
                                CG_ID          NUMERIC(18) NOT NULL,
                                TYPE           VARCHAR(20) NOT NULL,
                                NAME           VARCHAR(20) NOT NULL,
                                DESCRIPTION    VARCHAR(40) NOT NULL,
                                ACTIVE_FLAG    CHAR(1) DEFAULT 'Y' NOT NULL,

                                CONSTRAINT IPK_CODE_GENERIC PRIMARY KEY (CG_ID) DEFAULT nextval('S_CODE_GENERIC_PK')
                           ) TABLESPACE GENERAL_INDEX;

CREATE UNIQUE INDEX IUK_CODE_GENERIC_NAME ON CODE_GENERIC (TYPE, NAME) TABLESPACE GENERAL_INDEX;

ALTER TABLE CODE_GENERIC ADD ( CHECK (ACTIVE_FLAG in ('Y','N') );