#!/bin/sh
sudo service network-manager stop
sudo ./airmon-ng stop mon0
sudo ./airmon-ng start wlan4

