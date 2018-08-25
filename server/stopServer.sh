#!/bin/sh

sudo kill -9 `ps -A | grep AirSec | awk '{print $1}'`
