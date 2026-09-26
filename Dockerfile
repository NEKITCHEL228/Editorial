FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

COPY target/dependency/ libs/

EXPOSE 6767

ENTRYPOINT ["java", "-cp", "app.jar:libs/*", "application.Application"]