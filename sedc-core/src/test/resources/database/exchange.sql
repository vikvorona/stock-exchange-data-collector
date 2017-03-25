INSERT INTO EXCHANGE (ex_id, name, description, country, region_cg_id, timezone_utc_diff, open_local, close_local, last_update_tm)
VALUES(1, 'NYSE', 'New York Stock Exchange', 'United States', null, -5, CAST( '09:30:00' AS TIME), CAST( '16:00:00' AS TIME), now());

INSERT INTO EXCHANGE (ex_id, name, description, country, region_cg_id, timezone_utc_diff, open_local, close_local, last_update_tm)
VALUES(2, 'NASDAQ', 'NASDAQ', 'United States', null, -5, CAST( '09:30:00' AS TIME), CAST( '16:00:00' AS TIME), now());

INSERT INTO EXCHANGE (ex_id, name, description, country, region_cg_id, timezone_utc_diff, open_local, close_local, last_update_tm)
VALUES(3, 'LSE', 'London Stock Exchange Group', 'United Kingdom', null, 0, CAST( '08:00:00' AS TIME), CAST( '16:30:00' AS TIME), now());

INSERT INTO EXCHANGE (ex_id, name, description, country, region_cg_id, timezone_utc_diff, open_local, close_local, last_update_tm)
VALUES(4, 'JPX', 'Japan Exchange Group', 'Japan', null, 9, CAST( '09:00:00' AS TIME), CAST( '15:00:00' AS TIME), now());

INSERT INTO EXCHANGE (ex_id, name, description, country, region_cg_id, timezone_utc_diff, open_local, close_local, last_update_tm)
VALUES(5, 'SSE', 'Shanghai Stock Exchange', 'China', null, 8, CAST( '09:30:00' AS TIME), CAST( '15:00:00' AS TIME), now());

INSERT INTO EXCHANGE (ex_id, name, description, country, region_cg_id, timezone_utc_diff, open_local, close_local, last_update_tm)
VALUES(6, 'SEHK', 'Hong Kong Stock Exchange', 'Hong Kong', null, 8, CAST( '09:15:00' AS TIME), CAST( '16:00:00' AS TIME), now());

INSERT INTO EXCHANGE (ex_id, name, description, country, region_cg_id, timezone_utc_diff, open_local, close_local, last_update_tm)
VALUES(7, 'EURONEXT', 'Euronext', 'European Union', null, 1, CAST( '09:00:00' AS TIME), CAST( '17:30:00' AS TIME), now());

INSERT INTO EXCHANGE (ex_id, name, description, country, region_cg_id, timezone_utc_diff, open_local, close_local, last_update_tm)
VALUES(8, 'SZSE', 'Shenzhen Stock Exchange', 'China', null, 8, CAST( '09:30:00' AS TIME), CAST( '15:00:00' AS TIME), now());

INSERT INTO EXCHANGE (ex_id, name, description, country, region_cg_id, timezone_utc_diff, open_local, close_local, last_update_tm)
VALUES(9, 'SPBEX', 'Saint Petersburg Stock Exchange', 'Russia', null, 3, CAST( '10:30:00' AS TIME), CAST( '16:00:00' AS TIME), now());

INSERT INTO EXCHANGE (ex_id, name, description, country, region_cg_id, timezone_utc_diff, open_local, close_local, last_update_tm)
VALUES(10, 'MICEX', 'Moscow Exchange', 'Russia', null, 3, CAST( '09:30:00' AS TIME), CAST( '19:00:00' AS TIME), now());

