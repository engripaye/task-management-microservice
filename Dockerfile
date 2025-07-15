# Use official Maven + JDK image to build the app
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use a lighter JDK runtime for running the app
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/task-management-microservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
