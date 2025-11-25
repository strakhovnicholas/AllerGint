FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/diary-service-*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
