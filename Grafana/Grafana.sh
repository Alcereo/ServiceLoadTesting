#!/usr/bin/env bash

docker run --rm -i \
--name grafana  \
-p 3000:3000 \
grafana/grafana