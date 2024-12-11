FROM openjdk:17-jdk-slim
LABEL authors="andrii semonov"

WORKDIR /demo

COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]