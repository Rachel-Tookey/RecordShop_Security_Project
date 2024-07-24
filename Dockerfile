FROM maven:3-eclipse-temurin-22 AS build

WORKDIR /opt
COPY pom.xml .
RUN mvn dependency:resolve
COPY src ./src
RUN mvn clean package -D maven.test.skip

FROM eclipse-temurin:22-jre AS run

WORKDIR /opt
COPY --from=build /opt/target/group-project-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "group-project-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080

#RUN apt update -y && apt install -y nmap
#ENTRYPOINT ["sleep", "infinity"]