FROM eclipse-temurin:19-jdk-alpine as builder
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
