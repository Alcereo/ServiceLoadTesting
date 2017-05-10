#!/usr/bin/env bash

docker build --tag jmx_exporter ./ &&\
docker run --rm -i --network "host" --name jmx_exporter jmx_exporter