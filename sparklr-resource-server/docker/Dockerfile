FROM tomcat

ADD sparklr-resource-1.0-SNAPSHOT.war /tmp/

RUN rm -rf /usr/local/tomcat/webapps/*

RUN mv /tmp/sparklr-resource-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/sparklr-res.war