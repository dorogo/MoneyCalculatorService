# Stage 1: Build
FROM maven:3.8.3-openjdk-17 AS builder
WORKDIR /build
COPY ./ ./ 
# Собираем твой Spring Boot проект, пропуская тесты
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
# Копируем готовый JAR из builder
COPY --from=builder /build/target/MoneyCalculatorService-0.0.1-SNAPSHOT.jar /app.jar

# Порт приложения
EXPOSE 8180

# Команда запуска
ENTRYPOINT ["java", "-jar", "/app.jar"]
