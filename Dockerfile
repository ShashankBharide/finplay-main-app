# Use a Maven image with JDK 17 to build and run the application
FROM maven:3.9.4-eclipse-temurin-17

# Set the working directory
WORKDIR /app

# Copy the project files to the container
COPY pom.xml ./
COPY src ./src

# Package the application using Maven
RUN mvn clean package -DskipTests

# Expose the application port
EXPOSE 8001

# Run the application
ENTRYPOINT ["java", "-jar", "target/main-app-0.0.1-SNAPSHOT.jar"]
