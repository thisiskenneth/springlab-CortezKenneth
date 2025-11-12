#Etapa1: build
FROM gradle:8.4-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean bootJar

#Etapa2:Run \
FROM eclipse-temurin:17jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT["java","-jar","app.jar"]
