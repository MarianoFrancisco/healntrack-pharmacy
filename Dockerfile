FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pharmacy-service/pom.xml .

COPY pharmacy-service/src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/pharmacy-service-1.0.0.jar app.jar

EXPOSE 8003

ENTRYPOINT ["java", "-jar", "app.jar"]