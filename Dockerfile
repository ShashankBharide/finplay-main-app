# Use a Maven image for the build stage
FROM maven:3.9.4-amazoncorretto-17 as builder

# Set the working directory
WORKDIR /app

# Copy the project files to the container
COPY pom.xml ./pom.xml
COPY src ./src

# Package the application using Maven
RUN mvn clean package -DskipTests

# Use a lightweight JDK image for the runtime stage
FROM amazoncorretto:17-alpine

# Set the working directory
WORKDIR /app

# Copy the packaged application from the build stage
COPY --from=builder /app/target/main-app-0.0.1-SNAPSHOT.jar ./main-app.jar

# Expose the application port
EXPOSE 8001

# Run the application
ENTRYPOINT ["java", "-jar", "main-app.jar"]
