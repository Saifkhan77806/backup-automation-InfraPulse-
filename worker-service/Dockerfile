FROM eclipse-temurin:17-jdk-jammy

# Install rsync
RUN apt-get update && apt-get install -y rsync

WORKDIR /app

COPY target/worker-service.jar app.jar

# Create dummy folders
RUN mkdir -p /data/source /data/backup

ENTRYPOINT ["java", "-jar", "app.jar"]