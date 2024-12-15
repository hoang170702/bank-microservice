# start with a base image contaning java runtime
FROM openjdk:17-jdk-slim

LABEL authors="admin"

# add application's jar to the container
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java", "-jar", "/accounts-0.0.1-SNAPSHOT.jar"]