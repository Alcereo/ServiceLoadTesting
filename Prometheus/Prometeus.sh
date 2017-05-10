#!/usr/bin/env bash

docker build --tag prometheus ./ &&\
docker run --rm -i --name prometheus -p 9090:9090 prometheus