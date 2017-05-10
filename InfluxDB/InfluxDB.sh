#!/usr/bin/env bash

docker run --rm -i \
-p 8083:8083 \
-p 8086:8086 \
--name influxdb \
influxdb