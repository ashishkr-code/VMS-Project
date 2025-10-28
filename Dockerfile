# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven build output JAR file
COPY target/CVE-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8087 for Render
EXPOSE 8087

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
