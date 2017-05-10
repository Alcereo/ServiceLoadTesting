FROM openjdk:8-jre

RUN apt-get -y update

RUN apt-get -y install wget

RUN wget https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_httpserver/0.8/jmx_prometheus_httpserver-0.8-jar-with-dependencies.jar

ADD ./my_conf.yml  /jmx_conf.yml

CMD java -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.port=5555 -jar jmx_prometheus_httpserver-0.8-jar-with-dependencies.jar 5556 jmx_conf.yml
