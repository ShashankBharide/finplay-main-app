# Use your ECR Maven base image for the build stage
FROM 061039764011.dkr.ecr.us-east-1.amazonaws.com/maven-base:latest as builder
 
# Set the working directory
WORKDIR /app
 
# Copy the project files to the container
COPY pom.xml ./pom.xml
COPY src ./src
 
# Package the application using Maven
RUN mvn clean package -DskipTests
 
# Use a lightweight JDK image for the runtime stage
FROM 061039764011.dkr.ecr.us-east-1.amazonaws.com/maven-base
 
# Set the working directory
WORKDIR /app
 
# Copy the packaged application from the build stage
COPY --from=builder /app/target/main-app-0.0.1-SNAPSHOT.jar ./main-app.jar
 
# Expose the application port
EXPOSE 8001
 
# Run the application
ENTRYPOINT ["java", "-jar", "main-app.jar"]