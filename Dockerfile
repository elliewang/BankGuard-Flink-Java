FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q clean package -DskipTests
FROM flink:1.20.5-scala_2.12-java11
COPY --from=build /app/target/fraudstream-1.0.0-all.jar /opt/flink/usrlib/fraudstream.jar
