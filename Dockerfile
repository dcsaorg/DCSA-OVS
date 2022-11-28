FROM eclipse-temurin:17-jre-alpine

EXPOSE 9090
ENV db_hostname dcsa_db
COPY run-in-container.sh /run.sh
RUN chmod +x /run.sh
COPY ovs-service/src/main/resources/application.yml .
COPY ovs-service/target/dcsa-ovs-service.jar .
CMD java -jar dcsa-ovs-service.jar
