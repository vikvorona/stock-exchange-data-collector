#!/usr/bin/env bash

HOST_IP=$(hostname -I | awk '{print $1}')

# Install Portainer
docker run -d -p 8000:9000 --memory="100m" \
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
    nginx

echo "Nginx installed in Docker, ports 80,443"

# Install influxDB
docker run -d --memory="100m" \
    -p 8086:8086 -p 8083:8083 \
    --name influxdb \
    influxdb

echo "influxDB installed in Docker, ports 8086, 8083"

# Install Telegraf
docker run -d --restart=unless-stopped \
    --memory="100m" \
    -e "HOST_PROC=/proc" \
    -e "HOST_SYS=/sys" \
    -e "HOST_ETC=/etc" \
    -v $(pwd)/telegraf.conf:/etc/telegraf/telegraf.conf:ro \
    -v /var/run/docker.sock:/var/run/docker.sock:ro \
    -v /sys:/rootfs/sys:ro \
    -v /proc:/rootfs/proc:ro \
    -v /etc:/rootfs/etc:ro \
    --name telegraf \
    telegraf

echo "Telegraf installed in Docker"

# Install cAdvisor
docker run -d --memory="100m" \
    --volume=/:/rootfs:ro \
    --volume=/var/run:/var/run:rw \
    --volume=/sys:/sys:ro \
    --volume=/var/lib/docker/:/var/lib/docker:ro \
    --restart=unless-stopped \
    --publish=8010:8080 \
    --link=influxdb:influxdb \
    --name=cadvisor \
    google/cadvisor \
    -storage_driver=influxdb \
    -storage_driver_db=cadvisor \
    -storage_driver_host=influxdb:8086

echo "cAdvisor installed in Docker, ports 8010"

# Install Grafana
docker run -d -p 8020:3000 --memory="100m" \
    -e HTTP_USER=admin \
    -e HTTP_PASS=admin \
    -e INFLUXDB_HOST=localhost \
    -e INFLUXDB_PORT=8086 \
    -e INFLUXDB_NAME=cadvisor \
    -e INFLUXDB_USER=root \
    -e INFLUXDB_PASS=root \
    --link=influxdb:influxdb  \
    --restart=unless-stopped \
    --name=grafana \
    grafana/grafana

# Details https://dockerhanoi.wordpress.com/2015/08/19/docker-monitoring-with-cadvisor-influxdb-and-grafana/

echo "Grafana installed in Docker, port 8020"

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
