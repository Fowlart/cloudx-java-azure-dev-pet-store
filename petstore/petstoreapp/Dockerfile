# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package


# Package stage
FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
COPY --from=build /build/target/*.jar /app/petstoreapp.jar
ENTRYPOINT ["java","-jar","/app/petstoreapp.jar"]