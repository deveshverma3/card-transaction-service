# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:22-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the executable jar file from the build folder
COPY build/libs/card-transactions-service-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the port the app runs on
EXPOSE 8081