#!/usr/bin/env bash

docker build --tag test_service ./ &&\
docker run \
--rm \
--name test_service \
--network "host"    \
--cpus 4 \
--memory 300MB \
test_service

#-p 8080:8080 \
