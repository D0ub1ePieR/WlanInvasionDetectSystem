#!/bin/sh

sudo kill -9 `ps -A | grep AirSec | awk '{print $1}'`
cd ./server-packet-x64
./startAirSecurityServer.sh
