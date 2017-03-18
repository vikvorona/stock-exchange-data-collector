#!/bin/bash

HOST_IP=$(hostname -I | awk '{print $1}')

echo "IP: ${HOST_IP}"

echo "Removing old docker if exist"
sudo yum remove docker \
                  docker-common \
                  container-selinux \
                  docker-selinux \
                  docker-engine

echo "Adding Docker CE repository"
sudo yum config-manager \
    --add-repo \
    https://download.docker.com/linux/fedora/docker-ce.repo

sudo yum config-manager --enable docker-ce-edge

echo "Installing Docker CE"
sudo yum makecache fast
sudo yum install -y docker-ce

echo "$USER adding into docker group"
sudo usermod -aG docker $USER

echo "Disabling Docker-CE repository"
sudo yum config-manager --disable docker-ce-edge

echo "Adding Docker CE as service"
sudo systemctl enable docker
sudo systemctl start docker

echo "Ports usage:"
$(netstat -tupln)
