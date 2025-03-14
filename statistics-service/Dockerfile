FROM openjdk:21-jdk-slim AS builder
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x gradlew
RUN ./gradlew bootJar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar statistics-service-0.0.1-SNAPSHOT.jar.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "statistics-service-0.0.1-SNAPSHOT.jar.jar"]