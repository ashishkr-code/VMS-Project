# Multi-stage Dockerfile for Maven + Spring Boot (Java 24)
FROM maven:3.9.9-eclipse-temurin-24 AS build
WORKDIR /app

# Copy pom first to cache dependencies
COPY pom.xml .

# Download dependencies for cache
RUN mvn -B -f pom.xml -q dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -B -DskipTests package

# Runtime image (Java 24 JRE)
FROM eclipse-temurin:24-jre
WORKDIR /app

# Copy built JAR from builder stage
COPY --from=build /app/target/*.jar /app/app.jar

# Optional: set port environment
ENV JAVA_OPTS=""
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=$PORT -jar /app/app.jar"]