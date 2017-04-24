INSERT INTO EXCHANGE VALUES(nextval('S_EXCHANGE_PK'), 'NYSE', 'New York Stock Exchange', 'United States', GET_CG_ID_FROM_NAME('REGION', 'NAM'), -5, CAST( '09:30:00' AS TIME), CAST( '16:00:00' AS TIME), now());

INSERT INTO EXCHANGE VALUES(nextval('S_EXCHANGE_PK'), 'NASDAQ', 'NASDAQ', 'United States', GET_CG_ID_FROM_NAME('REGION', 'NAM'), -5, CAST( '09:30:00' AS TIME), CAST( '16:00:00' AS TIME), now());

INSERT INTO EXCHANGE VALUES(nextval('S_EXCHANGE_PK'), 'LSE', 'London Stock Exchange Group', 'United Kingdom', GET_CG_ID_FROM_NAME('REGION', 'EMEA'), 0, CAST( '08:00:00' AS TIME), CAST( '16:30:00' AS TIME), now());

INSERT INTO EXCHANGE VALUES(nextval('S_EXCHANGE_PK'), 'JPX', 'Japan Exchange Group', 'Japan', GET_CG_ID_FROM_NAME('REGION', 'APAC'), 9, CAST( '09:00:00' AS TIME), CAST( '15:00:00' AS TIME), now());

INSERT INTO EXCHANGE VALUES(nextval('S_EXCHANGE_PK'), 'SSE', 'Shanghai Stock Exchange', 'China', GET_CG_ID_FROM_NAME('REGION', 'APAC'), 8, CAST( '09:30:00' AS TIME), CAST( '15:00:00' AS TIME), now());

INSERT INTO EXCHANGE VALUES(nextval('S_EXCHANGE_PK'), 'SEHK', 'Hong Kong Stock Exchange', 'Hong Kong', GET_CG_ID_FROM_NAME('REGION', 'APAC'), 8, CAST( '09:15:00' AS TIME), CAST( '16:00:00' AS TIME), now());

INSERT INTO EXCHANGE VALUES(nextval('S_EXCHANGE_PK'), 'EURONEXT', 'Euronext', 'European Union', GET_CG_ID_FROM_NAME('REGION', 'EMEA'), 1, CAST( '09:00:00' AS TIME), CAST( '17:30:00' AS TIME), now());

INSERT INTO EXCHANGE VALUES(nextval('S_EXCHANGE_PK'), 'SZSE', 'Shenzhen Stock Exchange', 'China', GET_CG_ID_FROM_NAME('REGION', 'APAC'), 8, CAST( '09:30:00' AS TIME), CAST( '15:00:00' AS TIME), now());

INSERT INTO EXCHANGE VALUES(nextval('S_EXCHANGE_PK'), 'SPBEX', 'Saint Petersburg Stock Exchange', 'Russia', GET_CG_ID_FROM_NAME('REGION', 'EMEA'), 3, CAST( '10:30:00' AS TIME), CAST( '16:00:00' AS TIME), now());

INSERT INTO EXCHANGE VALUES(nextval('S_EXCHANGE_PK'), 'MICEX', 'Moscow Exchange', 'Russia', GET_CG_ID_FROM_NAME('REGION', 'EMEA'), 3, CAST( '09:30:00' AS TIME), CAST( '19:00:00' AS TIME), now());

INSERT INTO EXCHANGE VALUES(nextval('S_EXCHANGE_PK'), 'FOREX', 'Foreign Exchange', null, null, null, null, null, now());
