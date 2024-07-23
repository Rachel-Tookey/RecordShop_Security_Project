FROM eclipse-temurin:21-jdk
WORKDIR /src
COPY target/group-project-0.0.1-SNAPSHOT.jar /src/group-project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "group-project.jar"]