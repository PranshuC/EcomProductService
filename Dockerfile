#FROM ubuntu:22.04
# Because plain Linux OS, need every installation
#sudo apt-get update
#sudo apt-get install java

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17
# Copy the packaged JAR file to the container
COPY target/EcomProductService-0.0.1-SNAPSHOT.jar app.jar
# Expose the port the application runs on
EXPOSE 5050
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]