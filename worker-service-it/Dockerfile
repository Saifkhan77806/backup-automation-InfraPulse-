FROM eclipse-temurin:17-jdk-jammy

# Install required tools
RUN apt-get update && apt-get install -y rsync tar

WORKDIR /app

# Copy IT worker jar
COPY target/worker-service.jar app.jar

# Create folders for IT operations
RUN mkdir -p /data/it /data/it-copy

ENTRYPOINT ["java", "-jar", "app.jar"]