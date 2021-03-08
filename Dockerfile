FROM adoptopenjdk/openjdk11:latest

LABEL maintainer="koyr"

VOLUME /tmp

EXPOSE 8004

ARG JAR_FILE=target/service-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} service.jar

ENTRYPOINT ["java", "-jar", "service.jar"]