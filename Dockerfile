# Etapa 1: Build
FROM gradle:8.4-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean bootJar

# Etapa 2: Run
FROM eclipse-temurin:17-jdk
WORKDIR /app

# --- NUEVO ---
ARG APP_VERSION
ENV APP_VERSION=${APP_VERSION}

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
