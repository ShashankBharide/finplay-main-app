# Use a Maven image for building the application
FROM amazoncorretto:17-alpine
 
# Set the working directory
WORKDIR /app
 
# Copy the project files to the container
COPY pom.xml ./
COPY src ./src
 
# Package the application using Maven
RUN yum install -y maven && mvn clean package -DskipTests
 
# Set the working directory
WORKDIR /app
 
# Copy the packaged application from the build stage
COPY --from=builder /app/target/main-app-0.0.1-SNAPSHOT.jar ./main-app.jar
 
# Expose the application port
EXPOSE 8001
 
# Run the application
ENTRYPOINT ["java", "-jar", "main-app.jar"]