FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/gigachat-api-*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
