CREATE TABLESPACE test OWNER postgres LOCATION '/var/lib/postgresql/data/pg_tblspc/TestTablespace';
CREATE TABLESPACE prod OWNER postgres LOCATION '/var/lib/postgresql/data/pg_tblspc/SedcProdTablespace';
CREATE TABLESPACE uat OWNER postgres LOCATION '/var/lib/postgresql/data/pg_tblspc/SedcUatTablespace';

CREATE DATABASE "sedcp1" WITH OWNER = postgres ENCODING = 'UTF8' TEMPLATE = 'template0' TABLESPACE = sedcprod LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8' CONNECTION LIMIT = -1;
CREATE DATABASE "sedcu1" WITH OWNER = postgres ENCODING = 'UTF8' TEMPLATE = 'template0' TABLESPACE = sedcuat LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8' CONNECTION LIMIT = -1;

GRANT CONNECT ON DATABASE sedcp1 TO sedcprod;
GRANT CONNECT ON DATABASE sedcp1 TO sedcprodread;
GRANT CONNECT ON DATABASE sedcu1 TO sedcuat;
