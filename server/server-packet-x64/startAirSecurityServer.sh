#!/bin/bash

sudo export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./lib/
sudo ./AirSecurity_server -f ./AirSecurity.conf
