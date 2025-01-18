# Use the official OpenJDK image as a base image
FROM openjdk:17-oracle

# Set a working directory inside the container
WORKDIR /app

# Copy the application JAR file to the container
# Replace '1.0.0' with your current version if different
COPY target/group3-assignment-1.0.0-SNAPSHOT.jar app.jar

# Expose the port that your application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
