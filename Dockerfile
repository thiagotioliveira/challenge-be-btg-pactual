# Use the official image for OpenJDK binaries built by Eclipse Temurin
FROM eclipse-temurin:21-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the application jar file to the container
COPY impl/target/order-service-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose the port that your application will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "application.jar"]
