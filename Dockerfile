FROM eclipse-temurin:17-jre-alpine

EXPOSE 9090
ENV db_hostname dcsa_db
COPY target/dcsa_ovs-*.jar .
CMD java -jar dcsa_ovs-*.jar
