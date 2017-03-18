#!/usr/bin/env bash

HOST_IP=$(hostname -I | awk '{print $1}')

# Install Portainer
docker run -d -p 8000:8000 --memory="100m" \
    -v /var/run/docker.sock:/var/run/docker.sock \
    --restart=unless-stopped \
    --name=portainer \
    portainer/portainer

echo "Portainer installed in Docker, port 8000"

# Install Nginx
docker run -d -p 80:80 -p 443:443 --memory="100m" \
    -v /var/run/docker.sock:/var/run/docker.sock \
    --restart=unless-stopped \
    --name=nginx \
    nginx/nginx

echo "Nginx installed in Docker, ports 80,443"

# Install Grafana
docker run -d -p 8010:8010 --memory="100m" \
    -v /var/run/docker.sock:/var/run/docker.sock \
    --restart=unless-stopped \
    --name=grafana \
    grafana/grafana

echo "Grafana installed in Docker, port 8010"

# Zabbix parameters
DB_SERVER_PORT=3542
POSTGRES_USER="postgres"
POSTGRES_PASSWORD="postgres"
POSTGRES_DB="postgres"

docker run -d -t --memory="2g" --name postgres-server  \
      -p ${DB_SERVER_PORT}:5432 \
      -e POSTGRES_USER=${POSTGRES_USER} \
      -e POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
      -e POSTGRES_DB=${POSTGRES_DB} \
      -v /var/run/docker.sock:/var/run/docker.sock \
      postgres

echo "Postgres installed in Docker, port ${DB_SERVER_PORT}"

# Zabbix parameters
#ZBX_SERVER_NAME="SEDC"
#DB_SERVER_HOST=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres-server)
#
#docker run --name zabbix-snmptraps -t \
#      -v /zbx_instance/snmptraps:/var/lib/zabbix/snmptraps:rw \
#      -v /var/lib/zabbix/mibs:/usr/share/snmp/mibs:ro \
#      -p 162:162/udp \
#      -d zabbix/zabbix-snmptraps
#
#docker run --name zabbix-server-pgsql -t \
#      -e DB_SERVER_HOST=${DB_SERVER_HOST} \
#      -e POSTGRES_USER=${POSTGRES_USER} \
#      -e POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
#      -e ZBX_ENABLE_SNMP_TRAPS="true" \
#      --link postgres-server:postgres \
#      -p 10051:10051 \
#      --volumes-from zabbix-snmptraps \
#      -d zabbix/zabbix-server-pgsql
#
#docker run --name zabbix-web-apache-pgsql -t \
#      -e DB_SERVER_HOST=${DB_SERVER_HOST} \
#      -e POSTGRES_USER=${POSTGRES_USER} \
#      -e POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
#      --link postgres-server:postgres \
#      --link zabbix-server-pgsql:zabbix-server \
#      -p 8020:80 \
#      -p 8021:443 \
#      -v /etc/ssl/nginx:/etc/ssl/nginx:ro \
#      -d zabbix/zabbix-web-apache-pgsql
#
#echo "Zabbix installed in Docker, port 8010"
