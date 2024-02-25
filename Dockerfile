FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the Spring Boot application JAR file
COPY target/courier-tracking-0.0.1-SNAPSHOT.jar app.jar

# Copy the stores.json file
COPY stores.json .

# Expose the port your application listens on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]