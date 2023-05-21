#
# Build stage
#
FROM maven:3.8.5-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17
COPY --from=build /target/people-0.0.1-SNAPSHOT.jar people-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","people-0.0.1-SNAPSHOT.jar"]