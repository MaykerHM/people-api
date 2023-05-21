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
COPY --from=build /target/people.jar people.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","people.jar"]