# Используем образ с JDK для сборки приложения
FROM openjdk:21-jdk-slim AS builder
WORKDIR /app

# Копируем Gradle wrapper и файлы сборки
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Запускаем Gradle для загрузки зависимостей и сборки приложения
RUN chmod +x gradlew
RUN ./gradlew bootJar

# Создаем финальный образ на основе Alpine Linux
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Копируем собранный JAR-файл из предыдущего этапа
COPY --from=builder /app/build/libs/*.jar monitor-sensors-0.0.1-SNAPSHOT.jar

# Указываем порт, который будет прослушиваться приложением
EXPOSE 8080

# Запускаем приложение при старте контейнера
ENTRYPOINT ["java", "-jar", "monitor-sensors-0.0.1-SNAPSHOT.jar"]