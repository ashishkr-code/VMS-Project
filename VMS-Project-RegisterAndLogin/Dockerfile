# -------- Stage 1: Build (Maven) -----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Download dependencies first (faster rebuilds)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Stage 2: Runtime (Java) ----------
FROM openjdk:24-jdk-slim
WORKDIR /app

# Copy the packaged JAR from builder
COPY --from=build /app/target/*.jar app.jar

# Default port for local testing (Render will override)
ENV PORT=8087
EXPOSE 8087

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
