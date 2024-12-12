# Use a Maven image for building the application
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS builder
 
# Set the working directory
WORKDIR /app
 
# Copy the project files to the container
COPY pom.xml ./
COPY src ./src
 
# Package the application using Maven
RUN yum install -y maven && mvn clean package -DskipTests
 
# Use the same base image for runtime to avoid pull issues
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17-alpine
 
# Set the working directory
WORKDIR /app
 
# Copy the packaged application from the build stage
COPY --from=builder /app/target/main-app-0.0.1-SNAPSHOT.jar ./main-app.jar
 
# Expose the application port
EXPOSE 8001
 
# Run the application
ENTRYPOINT ["java", "-jar", "main-app.jar"]