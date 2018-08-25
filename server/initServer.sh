#!/bin/sh

cd ./server-packet-x64
sudo cp -rf AirSecurity /usr/local/lib
sudo cp -rf lib/* /usr/lib/
sudo ldconfig
