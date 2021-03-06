FROM adoptopenjdk/openjdk11:latest

LABEL maintainer="koyr"

VOLUME /tmp

EXPOSE 8084

ARG JAR_FILE=target/Service-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} Service.jar

ENTRYPOINT ["java", "-jar", "Service.jar"]