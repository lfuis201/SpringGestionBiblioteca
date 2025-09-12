# Stage 1: Build
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

# Copia los archivos de Maven
COPY pom.xml .
COPY src ./src

# Compila el proyecto y crea el JAR
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia solo el JAR compilado desde el stage de build
COPY --from=build /app/target/GestionBibliotecaUsuarios-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
