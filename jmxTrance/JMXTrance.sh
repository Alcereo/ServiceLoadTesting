#!/usr/bin/env bash

docker run -i --rm  \
--network "host"    \
--name jmxtrans     \
-v `pwd`/config.json:/var/lib/jmxtrans/config.json  \
-v `pwd`/docker-entrypoint.sh:/docker-entrypoint.sh \
-v `pwd`/jboss-cli-client.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/jboss-cli-client.jar \
-p 9999:9999 \
jmxtrans/jmxtrans
